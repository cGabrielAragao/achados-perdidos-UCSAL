package br.ucsal.achadosperdidos.apresentacao;

import java.time.LocalDate;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

/**
 * DTO do formulário de registro de objeto encontrado (RF01). Não expõe a
 * entidade {@code Objeto} ao template; a validação aqui (Bean Validation)
 * complementa, no formulário, as invariantes já garantidas pelo construtor
 * de {@code Objeto} (RN10).
 */
public class RegistroObjetoFormulario {

    @NotNull(message = "Selecione uma categoria.")
    private Categoria categoria;

    @NotBlank(message = "Informe uma descrição pública.")
    private String descricaoPublica;

    @NotBlank(message = "Informe a característica sensível (usada só para conferir a prova de posse).")
    private String caracteristicaSensivel;

    @NotNull(message = "Selecione o local onde o objeto foi encontrado.")
    private Long localId;

    @NotNull(message = "Informe a data em que o objeto foi encontrado.")
    @PastOrPresent(message = "A data do encontro não pode ser futura.")
    private LocalDate dataEncontro;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricaoPublica() {
        return descricaoPublica;
    }

    public void setDescricaoPublica(String descricaoPublica) {
        this.descricaoPublica = descricaoPublica;
    }

    public String getCaracteristicaSensivel() {
        return caracteristicaSensivel;
    }

    public void setCaracteristicaSensivel(String caracteristicaSensivel) {
        this.caracteristicaSensivel = caracteristicaSensivel;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public LocalDate getDataEncontro() {
        return dataEncontro;
    }

    public void setDataEncontro(LocalDate dataEncontro) {
        this.dataEncontro = dataEncontro;
    }
}
