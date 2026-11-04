package br.ucsal.achadosperdidos.dominio.objeto.busca;

import org.springframework.data.jpa.domain.Specification;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;

/**
 * Estratégia concreta (RF03): restringe a busca a uma única categoria.
 */
public class FiltroPorCategoria implements FiltroBusca {

    private final Categoria categoria;

    public FiltroPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public Specification<Objeto> toSpecification() {
        return (raiz, consulta, builder) -> builder.equal(raiz.get("categoria"), categoria);
    }
}
