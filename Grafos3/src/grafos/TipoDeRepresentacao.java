package grafos;

public enum TipoDeRepresentacao {
    MATRIZ_DE_ADJACENCIA(1),
    MATRIZ_DE_INCIDENCIA(2),
    LISTA_DE_ADJACENCIA(3);

    private final int tipoRe;

    TipoDeRepresentacao(int tipo) {
        tipoRe = tipo;
    }

    public int getTipoRe() {
        return tipoRe;
    }

    public static TipoDeRepresentacao fromTipoRe(int tipo) {
        for (TipoDeRepresentacao t : values()) {
            if (t.getTipoRe() == tipo) {
                return t;
            }
        }
        return null;
    }
}
