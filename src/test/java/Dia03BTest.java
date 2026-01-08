
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia03.BancoBaterias;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia03BTest {

    @Test
    @DisplayName("Validar soporte de tipos grandes (Long Overflow check)")
    public void testSoporteLong() {
        String input = "999999999999"; // 12 nueves
        long esperado = 999999999999L;
        long resultado = EstrategiasBusqueda.Greedy(input, 12);
        assertEquals(esperado, resultado, "El algoritmo debe retornar un Long válido sin desbordarse");
    }

    @Test
    @DisplayName("Validar lógica Greedy con 12 dígitos")
    public void testLogicaGreedy12() {

        String input = "1999999999999";
        long esperado = 999999999999L;
        long resultado = EstrategiasBusqueda.Greedy(input, 12);
        assertEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Integración: Suma total de un controlador configurado para 12 dígitos")
    public void testIntegracionFase2() {
        List<BancoBaterias> bancos = List.of(
                new BancoBaterias("999999999999"),
                new BancoBaterias("888888888888")
        );

        ProtocoloEnergia planB = s -> EstrategiasBusqueda.Greedy(s, 12);
        ControladorEscalera controlador = new ControladorEscalera(bancos, planB);
        long esperado = 999999999999L + 888888888888L;
        assertEquals(esperado, controlador.activar());
    }
}