package br.ucsal.achadosperdidos.infraestrutura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;

/**
 * Repositório JPA para {@link Objeto}. Estende {@link JpaSpecificationExecutor}
 * para aceitar as {@code Specification} montadas pelas estratégias de busca
 * (padrão Strategy) em {@code dominio.objeto.busca} — RF03.
 */
public interface ObjetoRepository extends JpaRepository<Objeto, Long>, JpaSpecificationExecutor<Objeto> {
}
