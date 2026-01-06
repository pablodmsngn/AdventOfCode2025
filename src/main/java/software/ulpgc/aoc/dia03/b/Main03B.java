package software.ulpgc.aoc.dia03.b;

import software.ulpgc.aoc.dia03.CargadorEntrada;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

public class Main03B {
    public static void main(String[] args) {

        ProtocoloEnergia planB = secuencia -> EstrategiasBusqueda.Greedy(secuencia, 12);

        // 2. CARGA (Usando la fachada)
        ControladorEscalera escalera = CargadorEntrada.cargar("dia03input", planB);

        // 3. EJECUCIÓN
        long resultado = escalera.activar();

        System.out.println("--- RESULTADO DÍA 3 ---");
        System.out.println("Sacudida Total (Plan B - 12 dígitos): " + resultado);
    }
}
