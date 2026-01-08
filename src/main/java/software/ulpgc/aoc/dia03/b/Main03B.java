package software.ulpgc.aoc.dia03.b;

import software.ulpgc.aoc.dia03.CargadorEntrada;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

public class Main03B {
    public static void main(String[] args) {

        ProtocoloEnergia planB = secuencia -> EstrategiasBusqueda.Greedy(secuencia, 12);

        ControladorEscalera escalera = CargadorEntrada.cargar("dia03input", planB);

        long resultado = escalera.activar();

        System.out.println("--- RESULTADO DÍA 3 ---");
        System.out.println("Sacudida Total (Plan B - 12 dígitos): " + resultado);
    }
}
