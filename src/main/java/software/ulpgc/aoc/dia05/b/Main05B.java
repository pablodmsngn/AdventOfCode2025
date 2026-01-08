package software.ulpgc.aoc.dia05.b;

import software.ulpgc.aoc.dia05.AuditorInventario;
import software.ulpgc.aoc.dia05.CargadorEntrada;
import software.ulpgc.aoc.dia05.ProtocoloFrescura;

public class Main05B {

    public static void main(String[] args) {

        ProtocoloFrescura dummy = (id, rangos) -> false;


        AuditorInventario auditor = CargadorEntrada.cargar("dia05input", dummy);


        long totalFrescos = auditor.calcularCoberturaTotal();

        System.out.println("--- RESULTADO DÍA 5 PARTE 2 ---");
        System.out.println("Total de IDs únicos considerados frescos: " + totalFrescos);
    }
}
