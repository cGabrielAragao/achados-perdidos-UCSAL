package br.ucsal.achadosperdidos.aplicacao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;
import br.ucsal.achadosperdidos.dominio.solicitacao.StatusSolicitacao;
import br.ucsal.achadosperdidos.infraestrutura.SolicitacaoRepository;

/**
 * Orquestra a moderação das solicitações de recuperação (RF06/RF07). As
 * regras (quem pode decidir o quê, e quando) ficam no domínio
 * ({@code SolicitacaoRecuperacao} e {@code Objeto}/{@code EstadoObjeto});
 * aqui só se busca a solicitação e se delega.
 */
@Service
public class ModeracaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    public ModeracaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoModeracaoDTO> listarPendentes() {
        return solicitacaoRepository.findByStatus(StatusSolicitacao.PENDENTE).stream()
                .map(SolicitacaoModeracaoDTO::deSolicitacao)
                .toList();
    }

    /** Aprovadas, mas ainda não devolvidas: aguardando RF07. */
    @Transactional(readOnly = true)
    public List<SolicitacaoModeracaoDTO> listarAprovadas() {
        return solicitacaoRepository.findByStatus(StatusSolicitacao.APROVADA).stream()
                .map(SolicitacaoModeracaoDTO::deSolicitacao)
                .toList();
    }

    /** RF06: aprova a solicitação; o objeto permanece EM_ANALISE até a devolução ser concluída (RF07). */
    @Transactional
    public void aprovar(Long solicitacaoId) {
        SolicitacaoRecuperacao solicitacao = buscar(solicitacaoId);
        solicitacao.aprovar();
        solicitacao.getObjeto().aprovarDevolucao();
        solicitacaoRepository.save(solicitacao);
    }

    /** RF06: recusa a solicitação; o objeto volta a DISPONIVEL (RN07). */
    @Transactional
    public void recusar(Long solicitacaoId) {
        SolicitacaoRecuperacao solicitacao = buscar(solicitacaoId);
        solicitacao.recusar();
        solicitacao.getObjeto().recusarSolicitacao();
        solicitacaoRepository.save(solicitacao);
    }

    /** RF07/RN08: só prossegue se a solicitação estiver APROVADA. */
    @Transactional
    public void concluirDevolucao(Long solicitacaoId) {
        SolicitacaoRecuperacao solicitacao = buscar(solicitacaoId);
        solicitacao.garantirAprovada();
        solicitacao.getObjeto().confirmarRecuperado();
    }

    private SolicitacaoRecuperacao buscar(Long solicitacaoId) {
        return solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada."));
    }
}
