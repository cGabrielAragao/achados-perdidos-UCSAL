package br.ucsal.achadosperdidos.dominio.objeto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * Objeto encontrado no campus. Núcleo do sistema (RF01-RF07).
 *
 * <p>A {@code caracteristicaSensivel} nunca é exposta em listagem ou
 * detalhe público (RN05); serve apenas para o moderador conferir a prova de
 * posse informada na solicitação de recuperação.
 *
 * <p>Não há {@code setStatus} público: o status nasce sempre
 * {@code DISPONIVEL} (RN01) e as transições seguintes só ocorrem através do
 * padrão State ({@link EstadoObjeto}), delegado pelos métodos de intenção
 * ({@code solicitarRecuperacao}, {@code aprovarDevolucao},
 * {@code recusarSolicitacao}, {@code confirmarRecuperado}).
 */
@Entity
@Table(name = "objeto")
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Categoria categoria;

    @Column(name = "descricao_publica", nullable = false)
    private String descricaoPublica;

    @Column(name = "caracteristica_sensivel", nullable = false)
    private String caracteristicaSensivel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    @Column(name = "data_encontro", nullable = false)
    private LocalDate dataEncontro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusObjeto status;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    protected Objeto() {
        // exigido pelo JPA
    }

    public Objeto(Categoria categoria, String descricaoPublica, String caracteristicaSensivel,
                   Local local, LocalDate dataEncontro) {
        validar(categoria, descricaoPublica, caracteristicaSensivel, local, dataEncontro);
        this.categoria = categoria;
        this.descricaoPublica = descricaoPublica;
        this.caracteristicaSensivel = caracteristicaSensivel;
        this.local = local;
        this.dataEncontro = dataEncontro;
        this.status = StatusObjeto.DISPONIVEL;
        this.dataRegistro = LocalDateTime.now();
    }

    /**
     * RN10: campos obrigatórios no registro; a data do encontro não pode
     * ser futura.
     */
    private static void validar(Categoria categoria, String descricaoPublica, String caracteristicaSensivel,
                                  Local local, LocalDate dataEncontro) {
        if (categoria == null) {
            throw new ObjetoInvalidoException("A categoria é obrigatória.");
        }
        if (descricaoPublica == null || descricaoPublica.isBlank()) {
            throw new ObjetoInvalidoException("A descrição pública é obrigatória.");
        }
        if (caracteristicaSensivel == null || caracteristicaSensivel.isBlank()) {
            throw new ObjetoInvalidoException("A característica sensível é obrigatória.");
        }
        if (local == null) {
            throw new ObjetoInvalidoException("O local é obrigatório.");
        }
        if (dataEncontro == null) {
            throw new ObjetoInvalidoException("A data do encontro é obrigatória.");
        }
        if (dataEncontro.isAfter(LocalDate.now())) {
            throw new ObjetoInvalidoException("A data do encontro não pode ser futura.");
        }
    }

    public Long getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getDescricaoPublica() {
        return descricaoPublica;
    }

    public String getCaracteristicaSensivel() {
        return caracteristicaSensivel;
    }

    public Local getLocal() {
        return local;
    }

    public LocalDate getDataEncontro() {
        return dataEncontro;
    }

    public StatusObjeto getStatus() {
        return status;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    /**
     * RF05/RN03/RN04: abre uma solicitação de recuperação. Exige prova de
     * posse e só é aceita se não houver outra solicitação pendente.
     */
    public void solicitarRecuperacao(String provaPosse) {
        if (provaPosse == null || provaPosse.isBlank()) {
            throw new ProvaObrigatoriaException();
        }
        getEstado().aoSolicitar(this);
    }

    /** RF06/RN06: moderador aprova a solicitação pendente. */
    public void aprovarDevolucao() {
        getEstado().aoAprovar(this);
    }

    /** RF06/RN07: moderador recusa a solicitação; objeto volta a DISPONIVEL. */
    public void recusarSolicitacao() {
        getEstado().aoRecusar(this);
    }

    /** RF07/RN08: conclui a devolução; objeto passa a RECUPERADO (estado final). */
    public void confirmarRecuperado() {
        getEstado().aoRecuperar(this);
    }

    private EstadoObjeto getEstado() {
        return EstadoObjetoFactory.paraStatus(this.status);
    }

    /**
     * Único ponto de mutação do status, usado exclusivamente pelos estados
     * (mesmo pacote). Não há {@code setStatus} público: nenhuma camada
     * externa pode pular uma transição.
     */
    void transicionarPara(StatusObjeto novoStatus) {
        this.status = novoStatus;
    }
}
