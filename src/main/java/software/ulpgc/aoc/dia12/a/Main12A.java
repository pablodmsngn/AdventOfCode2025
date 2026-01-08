package software.ulpgc.aoc.dia12.a;

import software.ulpgc.aoc.dia12.CargadorEntrada;
import software.ulpgc.aoc.dia12.ControladorGranja;

public class Main12A {
    public static void main(String[] args) {
        ControladorGranja granja = CargadorEntrada.cargar("dia12input");

        System.out.println("--- DÍA 12: GRANJA DE ÁRBOLES ---");
        long resultado = granja.contarRegionesValidas();

        System.out.println("Regiones donde caben todos los regalos: " + resultado);
    }
}