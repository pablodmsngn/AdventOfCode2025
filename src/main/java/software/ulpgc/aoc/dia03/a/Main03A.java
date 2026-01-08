package software.ulpgc.aoc.dia03.a;

import software.ulpgc.aoc.dia03.CargadorEntrada;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

public class Main03A {

    public static void main(String[] args) {

        ProtocoloEnergia planA = secuencia -> EstrategiasBusqueda.Greedy(secuencia, 2);

        ControladorEscalera escalera = CargadorEntrada.cargar("dia03input", planA);

        long resultado = escalera.activar();

        System.out.println("--- RESULTADO DÍA 3 ---");
        System.out.println("Sacudida Total (Plan A - 2 dígitos): " + resultado);
    }

}
