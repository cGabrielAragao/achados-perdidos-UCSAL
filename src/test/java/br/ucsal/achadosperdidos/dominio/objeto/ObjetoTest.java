package br.ucsal.achadosperdidos.dominio.objeto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testes de domínio do padrão State (RN01-RN09) e da obrigatoriedade da
 * prova de posse (RN04).
 */
class ObjetoTest {

    private Local laboratorio;

    @BeforeEach
    void preparar() {
        laboratorio = new Local("Laboratório de Informática");
    }

    private Objeto novoObjetoDisponivel() {
        return new Objeto(
                Categoria.FONE_DE_OUVIDO,
                "Fone de ouvido branco",
                "Adesivo azul dentro do estojo",
                laboratorio,
                LocalDate.now());
    }

    @Test
    void objetoNascoSempreDisponivel() {
        Objeto objeto = novoObjetoDisponivel();

        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
    }

    @Test
    void solicitarRecuperacaoComProvaValidaTransicionaParaEmAnalise() {
        Objeto objeto = novoObjetoDisponivel();

        objeto.solicitarRecuperacao("Tem um adesivo azul dentro do estojo");

        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.EM_ANALISE);
    }

    @Test
    void solicitarRecuperacaoSemProvaLancaExcecaoENaoAlteraStatus() {
        Objeto objeto = novoObjetoDisponivel();

        assertThatThrownBy(() -> objeto.solicitarRecuperacao(" "))
                .isInstanceOf(ProvaObrigatoriaException.class);
        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);

        assertThatThrownBy(() -> objeto.solicitarRecuperacao(null))
                .isInstanceOf(ProvaObrigatoriaException.class);
        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
    }

    @Test
    void naoAceitaNovaSolicitacaoEnquantoHouverUmaPendente() {
        Objeto objeto = novoObjetoDisponivel();
        objeto.solicitarRecuperacao("prova qualquer");

        assertThatThrownBy(() -> objeto.solicitarRecuperacao("outra prova"))
                .isInstanceOf(TransicaoInvalidaException.class);
        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.EM_ANALISE);
    }

    @Test
    void recusarSolicitacaoPendenteVoltaParaDisponivel() {
        Objeto objeto = novoObjetoDisponivel();
        objeto.solicitarRecuperacao("prova qualquer");

        objeto.recusarSolicitacao();

        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
    }

    @Test
    void confirmarRecuperadoComSolicitacaoPendenteTransicionaParaRecuperado() {
        Objeto objeto = novoObjetoDisponivel();
        objeto.solicitarRecuperacao("prova qualquer");

        objeto.aprovarDevolucao();
        objeto.confirmarRecuperado();

        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.RECUPERADO);
    }

    @Test
    void naoPermiteAprovarOuRecuperarSemSolicitacaoPendente() {
        Objeto objeto = novoObjetoDisponivel();

        assertThatThrownBy(objeto::aprovarDevolucao).isInstanceOf(TransicaoInvalidaException.class);
        assertThatThrownBy(objeto::recusarSolicitacao).isInstanceOf(TransicaoInvalidaException.class);
        assertThatThrownBy(objeto::confirmarRecuperado).isInstanceOf(TransicaoInvalidaException.class);
        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
    }

    @Test
    void recuperadoEhEstadoFinalQueNaoAceitaNenhumaTransicao() {
        Objeto objeto = novoObjetoDisponivel();
        objeto.solicitarRecuperacao("prova qualquer");
        objeto.confirmarRecuperado();

        assertThatThrownBy(() -> objeto.solicitarRecuperacao("nova prova"))
                .isInstanceOf(TransicaoInvalidaException.class);
        assertThatThrownBy(objeto::aprovarDevolucao).isInstanceOf(TransicaoInvalidaException.class);
        assertThatThrownBy(objeto::recusarSolicitacao).isInstanceOf(TransicaoInvalidaException.class);
        assertThatThrownBy(objeto::confirmarRecuperado).isInstanceOf(TransicaoInvalidaException.class);
        assertThat(objeto.getStatus()).isEqualTo(StatusObjeto.RECUPERADO);
    }
}
