package software.ulpgc.aoc.dia04;


import software.ulpgc.aoc.dia04.a.SolucionadorImprentaA;
import software.ulpgc.aoc.dia04.b.SolucionadorImprentaB;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Patrón Builder
 * Permite configurar paso a paso qué solucionador queremos (A o B) y con qué archivo,
 * haciendo el código cliente mucho más legible.
 */


public class FabricaEjecutador {
    private InputStream archivo;
    private TipoEjecutor tipo;

    public FabricaEjecutador desde(InputStream archivo) {
        this.archivo = archivo;
        return this;
    }

    public FabricaEjecutador tipo(TipoEjecutor tipo) {
        this.tipo = tipo;
        return this;
    }

    public Ejecutador construir() {
        if (archivo == null) throw new IllegalStateException("Falta input");

        List<String> lineas = new BufferedReader(new InputStreamReader(archivo))
                .lines()
                .toList();

        // Creamos el modelo de dominio una sola vez
        CuadriculaAlmacen almacenInicial = CuadriculaAlmacen.desde(lineas);

        // Inyectamos el MODELO, no el Stream
        if (tipo == TipoEjecutor.A) return new SolucionadorImprentaA(almacenInicial);
        return new SolucionadorImprentaB(almacenInicial);
    }

    public enum TipoEjecutor { A, B }
}