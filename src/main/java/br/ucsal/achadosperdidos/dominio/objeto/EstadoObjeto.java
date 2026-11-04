package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Padrão State (GoF): cada estado do ciclo de vida do {@link Objeto}
 * (RN01-RN09) conhece apenas as transições que lhe são permitidas.
 * {@code Objeto} delega a este contrato em vez de expor um setter público
 * de status.
 */
public interface EstadoObjeto {

    void aoSolicitar(Objeto objeto);

    void aoAprovar(Objeto objeto);

    void aoRecusar(Objeto objeto);

    void aoRecuperar(Objeto objeto);
}
