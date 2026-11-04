package br.ucsal.achadosperdidos.aplicacao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;

/**
 * Dados de um objeto para a tela de detalhe (RF04). Assim como
 * {@link ObjetoResumoDTO}, nunca carrega a característica sensível
 * (RN05) — não há como a apresentação exibi-la por engano.
 */
public record ObjetoDetalheDTO(
        Long id,
        Categoria categoria,
        String descricaoPublica,
        String nomeLocal,
        LocalDate dataEncontro,
        StatusObjeto status,
        LocalDateTime dataRegistro) {

    public static ObjetoDetalheDTO deObjeto(Objeto objeto) {
        return new ObjetoDetalheDTO(
                objeto.getId(),
                objeto.getCategoria(),
                objeto.getDescricaoPublica(),
                objeto.getLocal().getNome(),
                objeto.getDataEncontro(),
                objeto.getStatus(),
                objeto.getDataRegistro());
    }
}
