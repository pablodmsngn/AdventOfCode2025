package software.ulpgc.aoc.dia05.a;


import software.ulpgc.aoc.dia05.AuditorInventario;
import software.ulpgc.aoc.dia05.CargadorEntrada;
import software.ulpgc.aoc.dia05.ProtocoloFrescura;

public class Main05A {

    public static void main(String[] args) {

        // Definimos CÓMO saber si es fresco: "Si está contenido en alguno de los rangos"
        ProtocoloFrescura politicaEstandar = (id, rangos) ->
                rangos.stream().anyMatch(rango -> rango.contiene(id));

        AuditorInventario auditor = CargadorEntrada.cargar("dia05input", politicaEstandar);

        long resultado = auditor.auditar();

        System.out.println("--- RESULTADO DÍA 5 ---");
        System.out.println("Ingredientes frescos encontrados: " + resultado);
    }
}