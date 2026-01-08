package software.ulpgc.aoc.dia07.b;

import software.ulpgc.aoc.dia07.CargadorEntrada;
import software.ulpgc.aoc.dia07.ControladorLaboratorio;

public class Main07B {
    public static void main(String[] args) {
        ControladorLaboratorio laboratorio = CargadorEntrada.cargar("dia07input");

        System.out.println("--- DÍA 7 PARTE 2: MULTIVERSO TAQUIÓNICO ---");

        long totalLineas = laboratorio.contarLineasTemporales();
        System.out.println("Total de líneas temporales activas: " + totalLineas);
    }
}