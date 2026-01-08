package software.ulpgc.aoc.dia11.a;

import software.ulpgc.aoc.dia11.CargadorEntrada;
import software.ulpgc.aoc.dia11.ControladorReactor;

public class Main11A {
    public static void main(String[] args) {
        ControladorReactor controlador = CargadorEntrada.cargar("dia11input");

        System.out.println("--- DÍA 11: REACTOR ---");
        long resultado = controlador.contarRutasTotales();
        System.out.println("Rutas totales de 'you' a 'out': " + resultado);
    }
}