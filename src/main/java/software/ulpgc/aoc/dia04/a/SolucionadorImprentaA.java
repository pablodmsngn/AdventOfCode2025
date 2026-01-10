package software.ulpgc.aoc.dia04.a;

import software.ulpgc.aoc.dia04.CuadriculaAlmacen;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.OptimizadorMontacargas;

public class SolucionadorImprentaA implements Ejecutador {
    private final CuadriculaAlmacen almacen;


    public SolucionadorImprentaA(CuadriculaAlmacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public long ejecutar() {

        return OptimizadorMontacargas.contarRollosAccesibles(almacen);
    }
}