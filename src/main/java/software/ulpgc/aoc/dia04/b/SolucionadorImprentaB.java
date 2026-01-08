package software.ulpgc.aoc.dia04.b;


import software.ulpgc.aoc.dia04.Coordenada;
import software.ulpgc.aoc.dia04.CuadriculaAlmacen;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.OptimizadorMontacargas;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SolucionadorImprentaB implements Ejecutador {
    private final InputStream entrada;

    public SolucionadorImprentaB(InputStream entrada) {
        this.entrada = entrada;
    }

    @Override
    public long ejecutar() {
        List<String> lineas = new BufferedReader(new InputStreamReader(entrada))
                .lines()
                .toList();
        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(lineas);
        long totalRetirados = 0;
        boolean hayCambios = true;
        while (hayCambios) {
            OptimizadorMontacargas optimizador = new OptimizadorMontacargas(almacen);
            List<Coordenada> rollosAccesibles = optimizador.obtenerUbicacionesAccesibles().toList();
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
