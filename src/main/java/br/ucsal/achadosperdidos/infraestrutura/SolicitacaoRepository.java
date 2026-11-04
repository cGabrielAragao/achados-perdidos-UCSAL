package br.ucsal.achadosperdidos.infraestrutura;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;

/**
 * Repositório JPA para {@link SolicitacaoRecuperacao}.
 */
public interface SolicitacaoRepository extends JpaRepository<SolicitacaoRecuperacao, Long> {
}
