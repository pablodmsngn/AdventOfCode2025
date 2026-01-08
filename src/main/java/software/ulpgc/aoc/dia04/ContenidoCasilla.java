package software.ulpgc.aoc.dia04;


/**
 * Enumerado que define qué puede haber en una casilla específica.
 * Ayuda a evitar el uso de 'chars' sueltos (@, .) por todo el código, dando tipos fuertes.
 */
public enum ContenidoCasilla {
    VACIO, ROLLO_PAPEL;

    /**
     * Factoría estática: Convierte el carácter del fichero en un tipo enumerado.
     * @param simbolo El carácter leído ('@' o '.')
     * @return El tipo correspondiente.
     */
    public static ContenidoCasilla desdeCaracter(char simbolo) {
        return simbolo == '@' ? ROLLO_PAPEL : VACIO;
    }

    /**
     * Convierte el enum de vuelta a String para imprimirlo si fuera necesario.
     */
    @Override
    public String toString() {
        return this == ROLLO_PAPEL ? "@" : ".";
    }
}
