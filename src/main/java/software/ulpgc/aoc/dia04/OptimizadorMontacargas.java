package software.ulpgc.aoc.dia04;

import java.util.stream.Stream;


public record OptimizadorMontacargas(CuadriculaAlmacen almacen) {
    public long contarRollosAccesibles() {
        return obtenerUbicacionesAccesibles().count();
    }

    public Stream<Coordenada> obtenerUbicacionesAccesibles() {
        return almacen.todasLasCoordenadas()
                .filter(pos -> almacen.contenidoEn(pos) == ContenidoCasilla.ROLLO_PAPEL)
                .filter(pos -> !estaBloqueado(pos));
    }

    private boolean estaBloqueado(Coordenada pos) {
        return pos.vecinos()
                .map(almacen::contenidoEn)
                .filter(contenido -> contenido == ContenidoCasilla.ROLLO_PAPEL)
                .count() >= 4;
    }
}