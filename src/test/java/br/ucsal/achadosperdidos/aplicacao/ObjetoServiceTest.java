package br.ucsal.achadosperdidos.aplicacao;

import static org.assertj.core.api.Assertions.assertThat;

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
import br.ucsal.achadosperdidos.infraestrutura.LocalRepository;
import br.ucsal.achadosperdidos.infraestrutura.ObjetoRepository;

/**
 * Testes de RF01/RF02/RF03/RF04 na camada de aplicação, usando um H2 em
 * memória isolado (via {@code @DataJpaTest}) — não toca o banco em arquivo
 * real.
 */
@DataJpaTest
class ObjetoServiceTest {

    @Autowired
    private ObjetoRepository objetoRepository;

    @Autowired
    private LocalRepository localRepository;

    private ObjetoService service;
    private Local local;

    @BeforeEach
    void preparar() {
        service = new ObjetoService(objetoRepository, localRepository);
        local = localRepository.save(new Local("Biblioteca Central"));
    }

    @Test
    void listarDisponiveisTraSomenteObjetosComStatusDisponivel() {
        Objeto disponivel = objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves", "Chaveiro em formato de estrela", local, LocalDate.now()));

        Objeto emAnalise = objetoRepository.save(new Objeto(
                Categoria.CELULAR, "Celular preto", "Capinha com foto de cachorro", local, LocalDate.now()));
        emAnalise.solicitarRecuperacao("Capinha com foto de cachorro");
        objetoRepository.save(emAnalise);

        Objeto recuperado = objetoRepository.save(new Objeto(
                Categoria.GUARDA_CHUVA, "Guarda-chuva preto", "Cabo com fita vermelha", local, LocalDate.now()));
        recuperado.solicitarRecuperacao("Cabo com fita vermelha");
        recuperado.aprovarDevolucao();
        recuperado.confirmarRecuperado();
        objetoRepository.save(recuperado);

        List<ObjetoResumoDTO> disponiveis = service.listarDisponiveis(null, null);

        assertThat(disponiveis).extracting(ObjetoResumoDTO::id).containsExactly(disponivel.getId());
    }

    @Test
    void dtoDeListagemNuncaCarregaCaracteristicaSensivel() {
        objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves", "Segredo nunca exibido", local, LocalDate.now()));

        assertThat(service.listarDisponiveis(null, null)).isNotEmpty();
        assertThat(nomesDosCampos(ObjetoResumoDTO.class)).doesNotContain("caracteristicaSensivel");
    }

    @Test
    void dtoDeDetalheNuncaCarregaCaracteristicaSensivel() {
        Objeto objeto = objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves", "Segredo nunca exibido", local, LocalDate.now()));

        ObjetoDetalheDTO detalhe = service.buscarDetalhe(objeto.getId()).orElseThrow();

        assertThat(detalhe.descricaoPublica()).isEqualTo("Molho de chaves");
        assertThat(nomesDosCampos(ObjetoDetalheDTO.class)).doesNotContain("caracteristicaSensivel");
    }

    @Test
    void registrarCriaObjetoDisponivelComOsDadosInformados() {
        Long id = service.registrar(
                Categoria.GARRAFA_OU_COPO, "Garrafa azul", "Nome gravado no fundo", local.getId(), LocalDate.now());

        Objeto salvo = objetoRepository.findById(id).orElseThrow();
        assertThat(salvo.getStatus()).isEqualTo(StatusObjeto.DISPONIVEL);
        assertThat(salvo.getDescricaoPublica()).isEqualTo("Garrafa azul");
    }

    @Test
    void filtrarSoPorCategoriaRetornaApenasAquelaCategoriaEntreOsDisponiveis() {
        Local outroLocal = localRepository.save(new Local("Cantina"));
        Objeto chavesLocal1 = criarDisponivel(Categoria.CHAVES, local);
        Objeto chavesLocal2 = criarDisponivel(Categoria.CHAVES, outroLocal);
        criarDisponivel(Categoria.CELULAR, local);
        criarEmAnalise(Categoria.CHAVES, local);

        List<ObjetoResumoDTO> resultado = service.listarDisponiveis(Categoria.CHAVES, null);

        assertThat(resultado).extracting(ObjetoResumoDTO::id)
                .containsExactlyInAnyOrder(chavesLocal1.getId(), chavesLocal2.getId());
    }

    @Test
    void filtrarSoPorLocalRetornaApenasAqueleLocalEntreOsDisponiveis() {
        Local outroLocal = localRepository.save(new Local("Cantina"));
        Objeto chavesLocal1 = criarDisponivel(Categoria.CHAVES, local);
        Objeto celularLocal1 = criarDisponivel(Categoria.CELULAR, local);
        criarDisponivel(Categoria.CHAVES, outroLocal);
        criarEmAnalise(Categoria.CHAVES, local);

        List<ObjetoResumoDTO> resultado = service.listarDisponiveis(null, local.getId());

        assertThat(resultado).extracting(ObjetoResumoDTO::id)
                .containsExactlyInAnyOrder(chavesLocal1.getId(), celularLocal1.getId());
    }

    @Test
    void filtrarPorCategoriaELocalCombinaOsDoisComAND() {
        Local outroLocal = localRepository.save(new Local("Cantina"));
        Objeto chavesLocal1 = criarDisponivel(Categoria.CHAVES, local);
        criarDisponivel(Categoria.CELULAR, local);
        criarDisponivel(Categoria.CHAVES, outroLocal);
        criarEmAnalise(Categoria.CHAVES, local);

        List<ObjetoResumoDTO> resultado = service.listarDisponiveis(Categoria.CHAVES, local.getId());

        assertThat(resultado).extracting(ObjetoResumoDTO::id).containsExactly(chavesLocal1.getId());
    }

    @Test
    void semFiltroRetornaTodosOsDisponiveis() {
        Objeto chaves = criarDisponivel(Categoria.CHAVES, local);
        Objeto celular = criarDisponivel(Categoria.CELULAR, local);
        criarEmAnalise(Categoria.GUARDA_CHUVA, local);

        List<ObjetoResumoDTO> resultado = service.listarDisponiveis(null, null);

        assertThat(resultado).extracting(ObjetoResumoDTO::id)
                .containsExactlyInAnyOrder(chaves.getId(), celular.getId());
    }

    @Test
    void objetoEmAnaliseOuRecuperadoNuncaApareceMesmoCasandoComOFiltro() {
        Objeto emAnalise = criarEmAnalise(Categoria.CHAVES, local);

        Objeto recuperado = objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves recuperado", "Chaveiro de pelúcia", local, LocalDate.now()));
        recuperado.solicitarRecuperacao("Chaveiro de pelúcia");
        recuperado.aprovarDevolucao();
        recuperado.confirmarRecuperado();
        objetoRepository.save(recuperado);

        List<ObjetoResumoDTO> porCategoriaELocal = service.listarDisponiveis(Categoria.CHAVES, local.getId());
        List<ObjetoResumoDTO> semFiltro = service.listarDisponiveis(null, null);

        assertThat(porCategoriaELocal).extracting(ObjetoResumoDTO::id)
                .doesNotContain(emAnalise.getId(), recuperado.getId());
        assertThat(semFiltro).extracting(ObjetoResumoDTO::id)
                .doesNotContain(emAnalise.getId(), recuperado.getId());
    }

    private Objeto criarDisponivel(Categoria categoria, Local localDoObjeto) {
        return objetoRepository.save(new Objeto(
                categoria, "Objeto de teste", "Característica sensível de teste", localDoObjeto, LocalDate.now()));
    }

    private Objeto criarEmAnalise(Categoria categoria, Local localDoObjeto) {
        Objeto objeto = criarDisponivel(categoria, localDoObjeto);
        objeto.solicitarRecuperacao("Prova qualquer");
        return objetoRepository.save(objeto);
    }

    private static List<String> nomesDosCampos(Class<?> tipo) {
        return Arrays.stream(tipo.getDeclaredFields()).map(Field::getName).toList();
    }
}
