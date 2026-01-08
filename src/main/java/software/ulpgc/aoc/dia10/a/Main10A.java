package software.ulpgc.aoc.dia10.a;

import software.ulpgc.aoc.dia10.CargadorEntrada;
import software.ulpgc.aoc.dia10.ControladorFabrica;

public class Main10A {
    public static void main(String[] args) {
        ControladorFabrica controlador = CargadorEntrada.cargar("dia10input");
        System.out.println("--- DÍA 10: FÁBRICA ---");
        System.out.println("Total pulsaciones mínimas: " + controlador.ejecutarParte1());
    }

    // El mensaje de error [0.491s][error][attach] failure (232)... es un aviso de la JVM relacionado con herramientas
    //de depuración o monitoreo (como jcmd) intentando conectarse al proceso. Es inofensivo y puedes ignorarlo con
    // seguridad, ya que no afecta a la lógica ni al resultado del programa.
}