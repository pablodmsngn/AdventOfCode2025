package software.ulpgc.aoc.dia04;

import java.util.stream.Stream;

/**
 * Contiene la lógica pura del problema (Algoritmo).
 * Se encarga de determinar qué rollos son accesibles para los montacargas.
 * Principio: Alta Cohesión (solo sabe de reglas de optimización).
 */
public record OptimizadorMontacargas(CuadriculaAlmacen almacen) {


    public long contarRollosAccesibles() {
        return obtenerUbicacionesAccesibles().count();
    }

    /**
     * Filtra todas las coordenadas del almacén quedándose solo con las que cumplen la condición.
     */
    public Stream<Coordenada> obtenerUbicacionesAccesibles() {
        return almacen.todasLasCoordenadas()
                // 1. Primer filtro: Solo nos interesan las casillas que tienen papel
                .filter(pos -> almacen.contenidoEn(pos) == ContenidoCasilla.ROLLO_PAPEL)
                // 2. Segundo filtro: Solo aquellas que NO están bloqueadas por vecinos
                .filter(pos -> !estaBloqueado(pos));
    }

    /**
     * Aplica la regla matemática del enunciado:
     * "Accesible si hay MENOS de 4 vecinos".
     * Lógicamente equivalente a: "BLOQUEADO si hay 4 O MÁS vecinos".
     */
    private boolean estaBloqueado(Coordenada pos) {
        return pos.vecinos()
                .map(almacen::contenidoEn) // Miramos qué hay en cada vecino
                .filter(contenido -> contenido == ContenidoCasilla.ROLLO_PAPEL) // Contamos solo los rollos
                .count() >= 4; // Si hay 4 o más, devuelve true (está bloqueado)
    }
}