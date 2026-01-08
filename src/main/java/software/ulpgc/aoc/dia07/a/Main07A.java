package software.ulpgc.aoc.dia07.a;


import software.ulpgc.aoc.dia07.CargadorEntrada;
import software.ulpgc.aoc.dia07.ControladorLaboratorio;

public class Main07A {
    public static void main(String[] args) {

        ControladorLaboratorio laboratorio = CargadorEntrada.cargar("dia07input");

        System.out.println("--- DÍA 7: LABORATORIOS ---");
        int resultado = laboratorio.contarDivisiones();
        System.out.println("Total de divisiones del haz: " + resultado);
    }
}
