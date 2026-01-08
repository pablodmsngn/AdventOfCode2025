package software.ulpgc.aoc.dia04;


public enum ContenidoCasilla {
    VACIO, ROLLO_PAPEL;

    public static ContenidoCasilla desdeCaracter(char simbolo) {
        return simbolo == '@' ? ROLLO_PAPEL : VACIO;
    }

    @Override
    public String toString() {
        return this == ROLLO_PAPEL ? "@" : ".";
    }
}
