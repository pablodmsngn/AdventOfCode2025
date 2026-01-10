package software.ulpgc.aoc.dia04;

import java.util.stream.Stream;


/**
 * REFACTORIZACIÓN:
 * Cambio a 'utility class' (métodos estáticos).
 * Motivo: Evitar instanciar un objeto nuevo en cada iteración del bucle de la Parte B.
 * Ahora es una función pura: recibe un estado (almacen) y devuelve un resultado.
 */
public class OptimizadorMontacargas {

    //evitar instanciación
    private OptimizadorMontacargas() {}

    public static long contarRollosAccesibles(CuadriculaAlmacen almacen) {
        return obtenerUbicacionesAccesibles(almacen).count();
    }

    public static Stream<Coordenada> obtenerUbicacionesAccesibles(CuadriculaAlmacen almacen) {
        return almacen.todasLasCoordenadas()
                .filter(pos -> almacen.contenidoEn(pos) == ContenidoCasilla.ROLLO_PAPEL)
                .filter(pos -> !estaBloqueado(pos, almacen));
    }

    private static boolean estaBloqueado(Coordenada pos, CuadriculaAlmacen almacen) {
        return pos.vecinos()
                .map(almacen::contenidoEn)
                .filter(contenido -> contenido == ContenidoCasilla.ROLLO_PAPEL)
                .count() >= 4;
    }
}