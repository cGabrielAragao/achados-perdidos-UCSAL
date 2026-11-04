package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Lançada quando uma recuperação é solicitada sem prova de posse (RN04).
 */
public class ProvaObrigatoriaException extends RuntimeException {

    public ProvaObrigatoriaException() {
        super("É obrigatório informar uma prova de posse para solicitar a recuperação.");
    }
}
