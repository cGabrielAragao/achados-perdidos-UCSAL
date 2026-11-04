package br.ucsal.achadosperdidos.dominio.objeto;

/**
 * Reconstrói o {@link EstadoObjeto} correspondente ao {@link StatusObjeto}
 * persistido, já que apenas o enum é gravado no banco (não o objeto de
 * estado).
 */
final class EstadoObjetoFactory {

    private EstadoObjetoFactory() {
    }

    static EstadoObjeto paraStatus(StatusObjeto status) {
        return switch (status) {
            case DISPONIVEL -> new Disponivel();
            case EM_ANALISE -> new EmAnalise();
            case RECUPERADO -> new Recuperado();
        };
    }
}
