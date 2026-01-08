package software.ulpgc.aoc.dia07.b;

import software.ulpgc.aoc.dia07.CargadorEntrada;
import software.ulpgc.aoc.dia07.ControladorLaboratorio;

public class Main07B {
    public static void main(String[] args) {
        // Reutilizamos la carga del fichero 'Day07Input.txt'
        ControladorLaboratorio laboratorio = CargadorEntrada.cargar("dia07input");

        System.out.println("--- DÍA 7 PARTE 2: MULTIVERSO TAQUIÓNICO ---");

        // Calculamos el total de líneas temporales
        long totalLineas = laboratorio.contarLineasTemporales();

        System.out.println("Total de líneas temporales activas: " + totalLineas);
    }
}