package br.ucsal.achadosperdidos.infraestrutura;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;

/**
 * Repositório JPA para {@link Objeto}.
 */
public interface ObjetoRepository extends JpaRepository<Objeto, Long> {
}
