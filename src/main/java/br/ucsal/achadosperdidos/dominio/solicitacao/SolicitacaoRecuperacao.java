package br.ucsal.achadosperdidos.dominio.solicitacao;

import java.time.LocalDateTime;

import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.ProvaObrigatoriaException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Solicitação de recuperação de um {@link Objeto}, com a prova de posse
 * informada por quem acredita ser o dono (RF05). Nasce sempre PENDENTE; a
 * decisão do moderador (RF06/RF07) é tratada em um incremento posterior.
 *
 * <p>O moderador que decide ainda não é modelado aqui: a autenticação
 * (RF08) é desejável e fica para depois do fluxo central completo.
 */
@Entity
@Table(name = "solicitacao_recuperacao")
public class SolicitacaoRecuperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "objeto_id", nullable = false)
    private Objeto objeto;

    @Column(name = "prova_posse", nullable = false, length = 500)
    private String provaPosse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSolicitacao status;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_decisao")
    private LocalDateTime dataDecisao;

    protected SolicitacaoRecuperacao() {
        // exigido pelo JPA
    }

    /** RN04: a prova de posse é obrigatória. */
    public SolicitacaoRecuperacao(Objeto objeto, String provaPosse) {
        if (objeto == null) {
            throw new IllegalArgumentException("O objeto é obrigatório.");
        }
        if (provaPosse == null || provaPosse.isBlank()) {
            throw new ProvaObrigatoriaException();
        }
        this.objeto = objeto;
        this.provaPosse = provaPosse;
        this.status = StatusSolicitacao.PENDENTE;
        this.dataSolicitacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public String getProvaPosse() {
        return provaPosse;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public LocalDateTime getDataDecisao() {
        return dataDecisao;
    }
}
