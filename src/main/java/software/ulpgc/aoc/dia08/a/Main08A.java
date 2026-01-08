package software.ulpgc.aoc.dia08.a;

import software.ulpgc.aoc.dia08.CargadorEntrada;

public class Main08A {
    public static void main(String[] args) {
        //enunciado: "conecta los 1000 pares de cajas de conexiones más cercanos"
        long resultado = CargadorEntrada.cargar("dia08input").ejecutar(1000);
        System.out.println("Resultado Día 8: " + resultado);
    }
}