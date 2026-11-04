package br.ucsal.achadosperdidos.aplicacao;

import java.time.LocalDateTime;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;
import br.ucsal.achadosperdidos.dominio.solicitacao.StatusSolicitacao;

/**
 * DTO exclusivo da área de moderação (RF06/RF07). É a ÚNICA exposição
 * autorizada da {@code caracteristicaSensivel} (RN05): aqui ela aparece
 * lado a lado com a {@code provaPosse} para o moderador comparar as duas
 * e decidir. Nunca deve ser usado pelas telas públicas — essas continuam
 * com {@link ObjetoResumoDTO}/{@link ObjetoDetalheDTO}, que não têm esse
 * campo.
 */
public record SolicitacaoModeracaoDTO(
        Long solicitacaoId,
        Long objetoId,
        String descricaoPublica,
        String caracteristicaSensivel,
        String provaPosse,
        StatusSolicitacao status,
        LocalDateTime dataSolicitacao) {

    public static SolicitacaoModeracaoDTO deSolicitacao(SolicitacaoRecuperacao solicitacao) {
        Objeto objeto = solicitacao.getObjeto();
        return new SolicitacaoModeracaoDTO(
                solicitacao.getId(),
                objeto.getId(),
                objeto.getDescricaoPublica(),
                objeto.getCaracteristicaSensivel(),
                solicitacao.getProvaPosse(),
                solicitacao.getStatus(),
                solicitacao.getDataSolicitacao());
    }
}
