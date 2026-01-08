package software.ulpgc.aoc.dia04.a;


import software.ulpgc.aoc.dia04.CargadorImprenta;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.FabricaEjecutador;

public class Main04A {

    public static void main(String[] args) {

        Ejecutador solucionador = CargadorImprenta.cargar(
                "dia04input",
                FabricaEjecutador.TipoEjecutor.A
        );

        long resultado = solucionador.ejecutar();

        System.out.println("--- DÍA 4: DEPARTAMENTO DE IMPRESIÓN ---");
        System.out.println("Rollos accesibles (Parte A): " + resultado);
    }
}