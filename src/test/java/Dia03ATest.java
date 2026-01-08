

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia03.BancoBaterias;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia03ATest {

    @Test
    @DisplayName("Caso enunciado: Máximo al inicio (98...)")
    public void testEjemplo1() {
        String input = "987654321111111";
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(98L, resultado);
    }

    @Test
    @DisplayName("Caso enunciado: Máximo en extremos (8...9)")
    public void testEjemplo2() {
        String input = "811111111111119";
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(89L, resultado);
    }

    @Test
    @DisplayName("Caso enunciado: Máximo al final (...78)")
    public void testEjemplo3() {
        String input = "234234234234278";
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(78L, resultado);
    }

    @Test
    @DisplayName("Caso enunciado: Máximo intercalado (9...2)")
    public void testEjemplo4() {
        String input = "818181911112111";
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(92L, resultado);
    }

    @Test
    @DisplayName("Integración: Suma total de un controlador configurado para 2 dígitos")
    public void testIntegracionFase1() {
        List<BancoBaterias> bancos = List.of(
                new BancoBaterias("987654321"), // da 98
                new BancoBaterias("111111191")  // da 19 (1 al inicio, 9 al final)
        );

        ProtocoloEnergia planA = s -> EstrategiasBusqueda.Greedy(s, 2);
        ControladorEscalera controlador = new ControladorEscalera(bancos, planA);
        assertEquals(117L, controlador.activar());
    }
}