package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Objeto registrado e visível nas buscas (RN01, RN02). Só aceita uma nova
 * solicitação de recuperação.
 */
public class Disponivel implements EstadoObjeto {

    @Override
    public void aoSolicitar(Objeto objeto) {
        objeto.transicionarPara(StatusObjeto.EM_ANALISE);
    }

    @Override
    public void aoAprovar(Objeto objeto) {
        throw new TransicaoInvalidaException("Não há solicitação pendente para aprovar.");
    }

    @Override
    public void aoRecusar(Objeto objeto) {
        throw new TransicaoInvalidaException("Não há solicitação pendente para recusar.");
    }

    @Override
    public void aoRecuperar(Objeto objeto) {
        throw new TransicaoInvalidaException("O objeto precisa estar em análise antes de ser recuperado.");
    }
}
