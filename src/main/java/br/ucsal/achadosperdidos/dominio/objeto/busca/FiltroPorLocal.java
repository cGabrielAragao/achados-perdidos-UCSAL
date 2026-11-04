package br.ucsal.achadosperdidos.dominio.objeto.busca;

import org.springframework.data.jpa.domain.Specification;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;

/**
 * Estratégia concreta (RF03): restringe a busca a um único local.
 */
public class FiltroPorLocal implements FiltroBusca {

    private final Long localId;

    public FiltroPorLocal(Long localId) {
        this.localId = localId;
    }

    @Override
    public Specification<Objeto> toSpecification() {
        return (raiz, consulta, builder) -> builder.equal(raiz.get("local").get("id"), localId);
    }
}
