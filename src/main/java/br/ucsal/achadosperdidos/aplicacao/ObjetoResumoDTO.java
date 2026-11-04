package br.ucsal.achadosperdidos.aplicacao;

import java.time.LocalDate;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;

/**
 * Dados de um objeto para a listagem pública (RF02). Deliberadamente não
 * carrega a característica sensível: a ocultação (RN05) é garantida pela
 * ausência do campo, não por filtragem na apresentação.
 */
public record ObjetoResumoDTO(
        Long id,
        Categoria categoria,
        String descricaoPublica,
        String nomeLocal,
        LocalDate dataEncontro,
        StatusObjeto status) {

    public static ObjetoResumoDTO deObjeto(Objeto objeto) {
        return new ObjetoResumoDTO(
                objeto.getId(),
                objeto.getCategoria(),
                objeto.getDescricaoPublica(),
                objeto.getLocal().getNome(),
                objeto.getDataEncontro(),
                objeto.getStatus());
    }
}
