package software.ulpgc.aoc.dia04.b;



import software.ulpgc.aoc.dia04.CargadorImprenta;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.FabricaEjecutador;

public class Main04B {

    public static void main(String[] args) {
        // 1. CARGA: Usando la fachada para obtener el solucionador configurado
        Ejecutador solucionador = CargadorImprenta.cargar(
                "dia04input",
                FabricaEjecutador.TipoEjecutor.B
        );

        // 2. EJECUCIÓN: Polimorfismo en acción
        long resultado = solucionador.ejecutar();

        // 3. SALIDA
        System.out.println("--- DÍA 4: DEPARTAMENTO DE IMPRESIÓN ---");
        System.out.println("Rollos accesibles (Parte B): " + resultado);
    }
}