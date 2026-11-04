package br.ucsal.achadosperdidos.aplicacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Local;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;
import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoInvalidaException;
import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;
import br.ucsal.achadosperdidos.dominio.solicitacao.StatusSolicitacao;
import br.ucsal.achadosperdidos.infraestrutura.LocalRepository;
import br.ucsal.achadosperdidos.infraestrutura.ObjetoRepository;
import br.ucsal.achadosperdidos.infraestrutura.SolicitacaoRepository;

/**
 * Testes de RF06/RF07 na camada de aplicação, usando um H2 em memória
 * isolado (via {@code @DataJpaTest}) — não toca o banco em arquivo real.
 */
@DataJpaTest
class ModeracaoServiceTest {

    @Autowired
    private ObjetoRepository objetoRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    private ModeracaoService moderacaoService;
    private SolicitacaoService solicitacaoService;
    private Local local;
    private Objeto objeto;

    @BeforeEach
    void preparar() {
        moderacaoService = new ModeracaoService(solicitacaoRepository);
        solicitacaoService = new SolicitacaoService(objetoRepository, solicitacaoRepository);
        local = localRepository.save(new Local("Biblioteca Central"));
        objeto = objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves", "Chaveiro em formato de estrela", local, LocalDate.now()));
    }

    @Test
    void aprovarPendenteFicaAprovadaComDataDecisaoEObjetoSeguemEmAnalise() {
        Long solicitacaoId = solicitacaoService.solicitar(objeto.getId(), "Chaveiro em formato de estrela");

        moderacaoService.aprovar(solicitacaoId);

        SolicitacaoRecuperacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow();
        assertThat(solicitacao.getStatus()).isEqualTo(StatusSolicitacao.APROVADA);
        assertThat(solicitacao.getDataDecisao()).isNotNull();

        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.EM_ANALISE);
    }

    @Test
    void recusarPendenteFicaRecusadaEObjetoVoltaADisponivel() {
        Long solicitacaoId = solicitacaoService.solicitar(objeto.getId(), "Chaveiro em formato de estrela");

        moderacaoService.recusar(solicitacaoId);

        SolicitacaoRecuperacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow();
        assertThat(solicitacao.getStatus()).isEqualTo(StatusSolicitacao.RECUSADA);

        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
    }

    @Test
    void concluirComSolicitacaoAprovadaLevaObjetoARecuperado() {
        Long solicitacaoId = solicitacaoService.solicitar(objeto.getId(), "Chaveiro em formato de estrela");
        moderacaoService.aprovar(solicitacaoId);

        moderacaoService.concluirDevolucao(solicitacaoId);

        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.RECUPERADO);
    }

    @Test
    void concluirComSolicitacaoPendenteLancaExcecaoEObjetoPermaneceEmAnalise() {
        Long solicitacaoId = solicitacaoService.solicitar(objeto.getId(), "Chaveiro em formato de estrela");

        assertThatThrownBy(() -> moderacaoService.concluirDevolucao(solicitacaoId))
                .isInstanceOf(SolicitacaoInvalidaException.class);

        Objeto objetoAtualizado = objetoRepository.findById(objeto.getId()).orElseThrow();
        assertThat(objetoAtualizado.getStatus()).isEqualTo(StatusObjeto.EM_ANALISE);
    }

    @Test
    void cicloCompletoRegistrarSolicitarAprovarConcluirRemoveObjetoDasBuscas() {
        ObjetoService objetoService = new ObjetoService(objetoRepository, localRepository);
        Long objetoId = objetoService.registrar(
                Categoria.CELULAR, "Celular preto", "Capinha com foto de estrela", local.getId(), LocalDate.now());

        Long solicitacaoId = solicitacaoService.solicitar(objetoId, "Capinha com foto de estrela");
        moderacaoService.aprovar(solicitacaoId);
        moderacaoService.concluirDevolucao(solicitacaoId);

        Objeto objetoFinal = objetoRepository.findById(objetoId).orElseThrow();
        assertThat(objetoFinal.getStatus()).isEqualTo(StatusObjeto.RECUPERADO);

        List<ObjetoResumoDTO> disponiveis = objetoService.listarDisponiveis(null, null);
        assertThat(disponiveis).extracting(ObjetoResumoDTO::id).doesNotContain(objetoId);
    }

    @Test
    void dtoDeModeracaoCarregaCaracteristicaSensivelMasDtosPublicosContinuamSemEla() {
        solicitacaoService.solicitar(objeto.getId(), "Chaveiro em formato de estrela");

        List<SolicitacaoModeracaoDTO> pendentes = moderacaoService.listarPendentes();

        assertThat(pendentes).hasSize(1);
        assertThat(pendentes.get(0).caracteristicaSensivel()).isEqualTo("Chaveiro em formato de estrela");
        assertThat(pendentes.get(0).provaPosse()).isEqualTo("Chaveiro em formato de estrela");

        assertThat(nomesDosCampos(ObjetoResumoDTO.class)).doesNotContain("caracteristicaSensivel");
        assertThat(nomesDosCampos(ObjetoDetalheDTO.class)).doesNotContain("caracteristicaSensivel");
    }

    private static List<String> nomesDosCampos(Class<?> tipo) {
        return Arrays.stream(tipo.getDeclaredFields()).map(Field::getName).toList();
    }
}
