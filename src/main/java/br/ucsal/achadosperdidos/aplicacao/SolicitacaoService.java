package br.ucsal.achadosperdidos.aplicacao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;
import br.ucsal.achadosperdidos.infraestrutura.ObjetoRepository;
import br.ucsal.achadosperdidos.infraestrutura.SolicitacaoRepository;

/**
 * Orquestra a abertura de solicitações de recuperação (RF05). A validação
 * da prova (RN04) e a transição DISPONIVEL -> EM_ANALISE (RN03) ficam no
 * próprio {@link Objeto} (padrão State) — aqui só se garante que a
 * solicitação e a transição do objeto acontecem na mesma transação.
 */
@Service
public class SolicitacaoService {

    private final ObjetoRepository objetoRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public SolicitacaoService(ObjetoRepository objetoRepository, SolicitacaoRepository solicitacaoRepository) {
        this.objetoRepository = objetoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public Long solicitar(Long objetoId, String provaPosse) {
        Objeto objeto = objetoRepository.findById(objetoId)
                .orElseThrow(() -> new IllegalArgumentException("Objeto não encontrado."));

        // Se a prova for vazia (RN04) ou já houver solicitação pendente
        // (RN03), esta chamada lança a exceção de domínio correspondente
        // antes de qualquer SolicitacaoRecuperacao ser criada.
        objeto.solicitarRecuperacao(provaPosse);

        SolicitacaoRecuperacao solicitacao = new SolicitacaoRecuperacao(objeto, provaPosse);
        solicitacaoRepository.save(solicitacao);
        objetoRepository.save(objeto);

        return solicitacao.getId();
    }
}
