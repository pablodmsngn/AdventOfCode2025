package software.ulpgc.aoc.dia10.b;

import software.ulpgc.aoc.dia10.CargadorEntrada;
import software.ulpgc.aoc.dia10.ControladorFabrica;

public class Main10B {
    public static void main(String[] args) {

        ControladorFabrica controlador = CargadorEntrada.cargar("dia10input");

        System.out.println("--- DÍA 10 PARTE 2: REQUISITOS DE SACUDIDAS ---");
        long resultado = controlador.ejecutarParte2();

        System.out.println("Total pulsaciones mínimas: " + resultado);
    }
}