package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Objeto com uma solicitação de recuperação pendente (RN03). Enquanto
 * estiver neste estado, não aceita novas solicitações.
 */
public class EmAnalise implements EstadoObjeto {

    @Override
    public void aoSolicitar(Objeto objeto) {
        throw new TransicaoInvalidaException("Já existe uma solicitação pendente para este objeto.");
    }

    @Override
    public void aoAprovar(Objeto objeto) {
        // A aprovação do moderador não altera o status do objeto: apenas
        // confirma a prova (RN06). A transição final só ocorre quando a
        // devolução é de fato concluída, em aoRecuperar (RF07).
    }

    @Override
    public void aoRecusar(Objeto objeto) {
        objeto.transicionarPara(StatusObjeto.DISPONIVEL);
    }

    @Override
    public void aoRecuperar(Objeto objeto) {
        objeto.transicionarPara(StatusObjeto.RECUPERADO);
    }
}
