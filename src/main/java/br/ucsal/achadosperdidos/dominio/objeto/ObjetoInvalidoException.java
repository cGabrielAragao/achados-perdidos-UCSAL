package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Lançada quando os dados informados para registrar um {@link Objeto}
 * violam as invariantes de cadastro (RN10): campos obrigatórios ausentes
 * ou data do encontro futura.
 */
public class ObjetoInvalidoException extends RuntimeException {

    public ObjetoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
