package br.ucsal.achadosperdidos.dominio.solicitacao;

/**
 * Lançada quando se tenta decidir (aprovar/recusar) uma solicitação que já
 * não está PENDENTE, ou concluir uma devolução (RN08) sem a solicitação
 * estar APROVADA.
 */
public class SolicitacaoInvalidaException extends RuntimeException {

    public SolicitacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
