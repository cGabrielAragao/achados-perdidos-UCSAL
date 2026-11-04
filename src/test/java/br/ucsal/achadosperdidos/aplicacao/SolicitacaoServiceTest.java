package br.ucsal.achadosperdidos.aplicacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Local;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.ProvaObrigatoriaException;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;
import br.ucsal.achadosperdidos.dominio.objeto.TransicaoInvalidaException;
import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;
import br.ucsal.achadosperdidos.dominio.solicitacao.StatusSolicitacao;
import br.ucsal.achadosperdidos.infraestrutura.LocalRepository;
import br.ucsal.achadosperdidos.infraestrutura.ObjetoRepository;
import br.ucsal.achadosperdidos.infraestrutura.SolicitacaoRepository;

/**
 * Testes de RF05 na camada de aplicação, usando um H2 em memória isolado
 * (via {@code @DataJpaTest}) — não toca o banco em arquivo real.
 */
@DataJpaTest
class SolicitacaoServiceTest {

    @Autowired
    private ObjetoRepository objetoRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    private SolicitacaoService service;
    private Objeto objeto;

    @BeforeEach
    void preparar() {
        service = new SolicitacaoService(objetoRepository, solicitacaoRepository);
        Local local = localRepository.save(new Local("Biblioteca Central"));
        objeto = objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves", "Chaveiro em formato de estrela", local, LocalDate.now()));
    }

    @Test
    void solicitarComProvaValidaCriaSolicitacaoPendenteEColocaObjetoEmAnalise() {
        Long solicitacaoId = service.solicitar(objeto.getId(), "Chaveiro em formato de estrela");

        SolicitacaoRecuperacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow();
        assertThat(solicitacao.getStatus()).isEqualTo(StatusSolicitacao.PENDENTE);
        assertThat(solicitacao.getObjeto().getId()).isEqualTo(objeto.getId());
        assertThat(solicitacao.getDataDecisao()).isNull();

        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.EM_ANALISE);
    }

    @Test
    void solicitarSemProvaLancaExcecaoENaoCriaSolicitacaoNemAlteraStatus() {
        assertThatThrownBy(() -> service.solicitar(objeto.getId(), " "))
                .isInstanceOf(ProvaObrigatoriaException.class);

        assertThat(solicitacaoRepository.count()).isZero();
        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
    }

    @Test
    void segundaSolicitacaoEnquantoHaUmaPendenteEhRejeitadaENaoCriaNovaSolicitacao() {
        service.solicitar(objeto.getId(), "Chaveiro em formato de estrela");

        assertThatThrownBy(() -> service.solicitar(objeto.getId(), "Outra prova qualquer"))
                .isInstanceOf(TransicaoInvalidaException.class);

        assertThat(solicitacaoRepository.count()).isEqualTo(1);
        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.EM_ANALISE);
    }
}
