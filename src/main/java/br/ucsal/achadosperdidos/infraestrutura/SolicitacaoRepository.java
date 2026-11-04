package br.ucsal.achadosperdidos.infraestrutura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ucsal.achadosperdidos.dominio.solicitacao.SolicitacaoRecuperacao;
import br.ucsal.achadosperdidos.dominio.solicitacao.StatusSolicitacao;

/**
 * Repositório JPA para {@link SolicitacaoRecuperacao}.
 */
public interface SolicitacaoRepository extends JpaRepository<SolicitacaoRecuperacao, Long> {

    List<SolicitacaoRecuperacao> findByStatus(StatusSolicitacao status);
}
