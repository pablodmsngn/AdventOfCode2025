package software.ulpgc.aoc.dia08.b;


import software.ulpgc.aoc.dia08.CargadorEntrada;
import software.ulpgc.aoc.dia08.ControladorLuces;

public class Main08B {
    public static void main(String[] args) {

        ControladorLuces controlador = CargadorEntrada.cargar("dia08input");

        System.out.println("--- DÍA 8 PARTE 2: UNIFICACIÓN DE CIRCUITOS ---");
        long resultado = controlador.ejecutarUnificacion();

        System.out.println("Coste de la última conexión (X1 * X2): " + resultado);
    }
}