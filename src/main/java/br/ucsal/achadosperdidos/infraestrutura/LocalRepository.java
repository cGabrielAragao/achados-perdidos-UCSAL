package br.ucsal.achadosperdidos.infraestrutura;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ucsal.achadosperdidos.dominio.objeto.Local;

/**
 * Repositório JPA para {@link Local}.
 */
public interface LocalRepository extends JpaRepository<Local, Long> {

    Optional<Local> findByNome(String nome);
}
