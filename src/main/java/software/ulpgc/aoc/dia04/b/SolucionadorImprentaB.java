package software.ulpgc.aoc.dia04.b;

import software.ulpgc.aoc.dia04.Coordenada;
import software.ulpgc.aoc.dia04.CuadriculaAlmacen;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.OptimizadorMontacargas;

import java.util.List;

public class SolucionadorImprentaB implements Ejecutador {

    private CuadriculaAlmacen almacen;

    public SolucionadorImprentaB(CuadriculaAlmacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public long ejecutar() {
        long totalRetirados = 0;
        boolean hayCambios = true;
        while (hayCambios) {
            List<Coordenada> rollosAccesibles = OptimizadorMontacargas
                    .obtenerUbicacionesAccesibles(almacen)
                    .toList();
            if (rollosAccesibles.isEmpty()) {
                hayCambios = false;
            } else {
                totalRetirados += rollosAccesibles.size();
                almacen = almacen.retirarRollos(rollosAccesibles);
            }
        }
        return totalRetirados;
    }
}