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
 * <p>Não há {@code setStatus} público: neste incremento o status nasce
 * sempre {@code DISPONIVEL} (RN01) e as transições (padrão State) serão
 * implementadas no Incremento 3.
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
        this.categoria = categoria;
        this.descricaoPublica = descricaoPublica;
        this.caracteristicaSensivel = caracteristicaSensivel;
        this.local = local;
        this.dataEncontro = dataEncontro;
        this.status = StatusObjeto.DISPONIVEL;
        this.dataRegistro = LocalDateTime.now();
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
}
