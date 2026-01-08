package software.ulpgc.aoc.dia11.b;


import software.ulpgc.aoc.dia11.CargadorEntrada;
import software.ulpgc.aoc.dia11.ControladorReactor;

public class Main11B {
    public static void main(String[] args) {
        ControladorReactor controlador = CargadorEntrada.cargar("dia11input");

        System.out.println("--- DÍA 11 PARTE 2: RUTAS CRÍTICAS ---");
        long resultado = controlador.contarRutasCriticas();

        System.out.println("Rutas desde 'svr' a 'out' pasando por 'dac' y 'fft': " + resultado);
    }
}
