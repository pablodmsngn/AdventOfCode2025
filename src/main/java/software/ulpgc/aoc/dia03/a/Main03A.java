package software.ulpgc.aoc.dia03.a;

import software.ulpgc.aoc.dia03.CargadorEntrada;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

public class Main03A {

    public static void main(String[] args) {
        // 1. CONFIGURACIÓN DE ESTRATEGIA (Lambda Adapter)
        // Adaptamos 'Greedy(String, int)' a 'ProtocoloEnergia(String)'
        // Aquí decidimos que queremos buscar 2 dígitos (Plan A)
        ProtocoloEnergia planA = secuencia -> EstrategiasBusqueda.Greedy(secuencia, 2);

        // 2. CARGA (Usando la fachada)
        ControladorEscalera escalera = CargadorEntrada.cargar("dia03input", planA);

        // 3. EJECUCIÓN
        long resultado = escalera.activar();

        System.out.println("--- RESULTADO DÍA 3 ---");
        System.out.println("Sacudida Total (Plan A - 2 dígitos): " + resultado);
    }

}
