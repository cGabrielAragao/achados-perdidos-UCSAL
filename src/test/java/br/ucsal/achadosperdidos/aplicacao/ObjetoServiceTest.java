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
 * Testes de RF01/RF02/RF04 na camada de aplicação, usando um H2 em memória
 * isolado (via {@code @DataJpaTest}) — não toca o banco em arquivo real.
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

        List<ObjetoResumoDTO> disponiveis = service.listarDisponiveis();

        assertThat(disponiveis).extracting(ObjetoResumoDTO::id).containsExactly(disponivel.getId());
    }

    @Test
    void dtoDeListagemNuncaCarregaCaracteristicaSensivel() {
        objetoRepository.save(new Objeto(
                Categoria.CHAVES, "Molho de chaves", "Segredo nunca exibido", local, LocalDate.now()));

        assertThat(service.listarDisponiveis()).isNotEmpty();
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

    private static List<String> nomesDosCampos(Class<?> tipo) {
        return Arrays.stream(tipo.getDeclaredFields()).map(Field::getName).toList();
    }
}
