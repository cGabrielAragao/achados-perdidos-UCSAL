package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Status do ciclo de vida de um {@link Objeto} (RN01-RN09).
 * Neste incremento é apenas um enum persistido; as regras de transição
 * (padrão State) entram no Incremento 3.
 */
public enum StatusObjeto {
    DISPONIVEL,
    EM_ANALISE,
    RECUPERADO
}
