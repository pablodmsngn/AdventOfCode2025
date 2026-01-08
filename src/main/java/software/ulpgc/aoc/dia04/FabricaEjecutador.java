package software.ulpgc.aoc.dia04;


import software.ulpgc.aoc.dia04.a.SolucionadorImprentaA;
import software.ulpgc.aoc.dia04.b.SolucionadorImprentaB;

import java.io.InputStream;

/**
 * Patrón Builder / Factory.
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

    /**
     * Construye la instancia concreta (Polimorfismo).
     * Devuelve una interfaz 'Ejecutor04', ocultando qué clase real se usa.
     */
    public Ejecutador construir() {
        if (tipo == TipoEjecutor.A) return new SolucionadorImprentaA(archivo);
        return new SolucionadorImprentaB(archivo);
    }

    public enum TipoEjecutor { A, B }
}