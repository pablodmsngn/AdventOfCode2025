package software.ulpgc.aoc.dia09.a;


import software.ulpgc.aoc.dia09.CargadorEntrada;
import software.ulpgc.aoc.dia09.ControladorCine;

public class Main09A {
    public static void main(String[] args) {

        ControladorCine cine = CargadorEntrada.cargar("dia09input");

        System.out.println("--- DÍA 9: CINE ---");
        System.out.println("Área máxima posible: " + cine.obtenerAreaMaxima());
    }
}
