package br.ucsal.achadosperdidos.infraestrutura;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Local;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;

/**
 * Carrega dados fictícios de demonstração (locais e objetos) na primeira
 * execução. Como o H2 roda em modo arquivo e persiste entre execuções, a
 * carga só ocorre se a tabela de objetos estiver vazia, evitando duplicação
 * a cada novo boot da aplicação.
 */
@Component
public class CarregadorDadosDemonstracao implements CommandLineRunner {

    private final LocalRepository localRepository;
    private final ObjetoRepository objetoRepository;

    public CarregadorDadosDemonstracao(LocalRepository localRepository, ObjetoRepository objetoRepository) {
        this.localRepository = localRepository;
        this.objetoRepository = objetoRepository;
    }

    @Override
    public void run(String... args) {
        if (objetoRepository.count() > 0) {
            return;
        }

        Local biblioteca = localRepository.save(new Local("Biblioteca Central"));
        Local laboratorioInformatica = localRepository.save(new Local("Laboratório de Informática"));
        Local cantina = localRepository.save(new Local("Cantina"));
        Local salaDeAula12 = localRepository.save(new Local("Sala de Aula 12"));
        Local quadraEsportiva = localRepository.save(new Local("Quadra Esportiva"));

        objetoRepository.save(new Objeto(
                Categoria.FONE_DE_OUVIDO,
                "Fone de ouvido branco, sem fio",
                "Possui um pequeno adesivo azul na parte interna do estojo",
                laboratorioInformatica,
                LocalDate.now().minusDays(5)));

        objetoRepository.save(new Objeto(
                Categoria.GARRAFA_OU_COPO,
                "Garrafa de água de plástico, cor verde",
                "Tem o nome \"Marina\" escrito a caneta no fundo da garrafa",
                cantina,
                LocalDate.now().minusDays(3)));

        objetoRepository.save(new Objeto(
                Categoria.MATERIAL_DE_ESTUDO,
                "Caderno universitário capa dura, anotações de Cálculo",
                "Na contracapa há um bilhete colado escrito \"procurar sala 12\"",
                salaDeAula12,
                LocalDate.now().minusDays(2)));

        objetoRepository.save(new Objeto(
                Categoria.CHAVES,
                "Molho com três chaves e um chaveiro de borracha",
                "O chaveiro de borracha tem o formato de uma caveira pequena",
                biblioteca,
                LocalDate.now().minusDays(1)));

        objetoRepository.save(new Objeto(
                Categoria.VESTUARIO,
                "Moletom cinza com capuz, tamanho M",
                "Tem um pequeno rasgo de costura na manga esquerda, perto do punho",
                quadraEsportiva,
                LocalDate.now()));
    }
}
