package software.ulpgc.aoc.dia05.b;

import software.ulpgc.aoc.dia05.AuditorInventario;
import software.ulpgc.aoc.dia05.CargadorEntrada;
import software.ulpgc.aoc.dia05.ProtocoloFrescura;

public class Main05B {

    public static void main(String[] args) {
        // Para la parte 2, la estrategia de filtrado no se usa,
        // pero la arquitectura pide una. Pasamos una lambda vacía o nula.
        ProtocoloFrescura dummy = (id, rangos) -> false;

        // 1. CARGA
        AuditorInventario auditor = CargadorEntrada.cargar("dia05input", dummy);

        // 2. EJECUCIÓN (Nueva lógica de cobertura total)
        long totalFrescos = auditor.calcularCoberturaTotal();

        System.out.println("--- RESULTADO DÍA 5 PARTE 2 ---");
        System.out.println("Total de IDs únicos considerados frescos: " + totalFrescos);
    }
}
