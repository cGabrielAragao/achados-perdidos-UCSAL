package br.ucsal.achadosperdidos.infraestrutura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;

/**
 * Repositório JPA para {@link Objeto}.
 */
public interface ObjetoRepository extends JpaRepository<Objeto, Long> {

    List<Objeto> findByStatus(StatusObjeto status);
}
