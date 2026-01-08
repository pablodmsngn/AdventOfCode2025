package software.ulpgc.aoc.dia04.a;


import software.ulpgc.aoc.dia04.CargadorImprenta;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.FabricaEjecutador;

public class Main04A {

    public static void main(String[] args) {
        // 1. CARGA: Usando la fachada para obtener el solucionador configurado
        Ejecutador solucionador = CargadorImprenta.cargar(
                "dia04input",
                FabricaEjecutador.TipoEjecutor.A
        );

        // 2. EJECUCIÓN: Polimorfismo en acción
        long resultado = solucionador.ejecutar();

        // 3. SALIDA
        System.out.println("--- DÍA 4: DEPARTAMENTO DE IMPRESIÓN ---");
        System.out.println("Rollos accesibles (Parte A): " + resultado);
    }
}