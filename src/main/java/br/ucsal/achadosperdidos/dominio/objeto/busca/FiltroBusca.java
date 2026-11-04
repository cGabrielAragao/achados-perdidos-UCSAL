package br.ucsal.achadosperdidos.dominio.objeto.busca;

import org.springframework.data.jpa.domain.Specification;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;

/**
 * Padrão Strategy (GoF): cada critério de busca (categoria, local, ...) é
 * uma estratégia que sabe converter a si mesma em uma {@code Specification}
 * do Spring Data. O {@code ObjetoService} só conhece este contrato — para
 * aceitar um novo critério de busca basta criar uma nova classe que o
 * implemente, sem alterar o service (OCP) nem empilhar {@code if}s para
 * decidir qual filtro aplicar.
 */
public interface FiltroBusca {

    Specification<Objeto> toSpecification();

    /**
     * RN02: só objetos DISPONIVEL aparecem na busca. Não é uma estratégia
     * opcional como as demais (sempre se aplica), por isso fica como
     * especificação fixa aqui, e não como mais uma implementação de
     * {@code FiltroBusca}.
     */
    static Specification<Objeto> disponivel() {
        return (raiz, consulta, builder) -> builder.equal(raiz.get("status"), StatusObjeto.DISPONIVEL);
    }
}
