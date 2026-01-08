package software.ulpgc.aoc.dia02.b;

import software.ulpgc.aoc.dia02.CargadorEntrada;
import software.ulpgc.aoc.dia02.EstrategiasValidacion;
import software.ulpgc.aoc.dia02.Motor;
import java.util.function.LongPredicate;

public class Main02b {
    public static void main(String[] args) {

        LongPredicate estrategia = EstrategiasValidacion.PATRON_B;
        Motor motor = CargadorEntrada.cargar("dia02input", estrategia);
        long resultado = motor.run();

        System.out.println("--- RESULTADO DÍA 2 ---");
        System.out.println("Suma de IDs inválidos: " + resultado);
    }
}