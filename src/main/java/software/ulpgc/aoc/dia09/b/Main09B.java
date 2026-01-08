package software.ulpgc.aoc.dia09.b;


import software.ulpgc.aoc.dia09.CargadorEntrada;
import software.ulpgc.aoc.dia09.ControladorCine;

public class Main09B {
    public static void main(String[] args) {
        ControladorCine cine = CargadorEntrada.cargar("dia09input");

        System.out.println("--- DÍA 9 PARTE 2: CINE ---");
        System.out.println("Área máxima permitida (Rojo/Verde): " + cine.obtenerAreaMaximaPermitida());
    }
}
