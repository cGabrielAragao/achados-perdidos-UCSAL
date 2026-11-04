package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Lançada quando se tenta uma transição de status não permitida pelo
 * estado atual do {@link Objeto} (padrão State, RN03-RN09).
 */
public class TransicaoInvalidaException extends RuntimeException {

    public TransicaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
