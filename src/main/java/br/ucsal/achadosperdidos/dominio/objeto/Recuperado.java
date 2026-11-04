package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Estado final e irreversível (RN08, RN09): o objeto foi devolvido ao
 * dono, não aparece mais em buscas e não aceita novas solicitações.
 */
public class Recuperado implements EstadoObjeto {

    @Override
    public void aoSolicitar(Objeto objeto) {
        throw new TransicaoInvalidaException("Objeto recuperado não aceita novas solicitações.");
    }

    @Override
    public void aoAprovar(Objeto objeto) {
        throw new TransicaoInvalidaException("Objeto recuperado é um estado final.");
    }

    @Override
    public void aoRecusar(Objeto objeto) {
        throw new TransicaoInvalidaException("Objeto recuperado é um estado final.");
    }

    @Override
    public void aoRecuperar(Objeto objeto) {
        throw new TransicaoInvalidaException("Objeto já está recuperado.");
    }
}
