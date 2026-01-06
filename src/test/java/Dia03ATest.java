

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia03.BancoBaterias;
import software.ulpgc.aoc.dia03.ControladorEscalera;
import software.ulpgc.aoc.dia03.EstrategiasBusqueda;
import software.ulpgc.aoc.dia03.ProtocoloEnergia;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Día 3 - Fase 1: Sacudida con 2 Baterías")
public class Dia03ATest {

    @Test
    @DisplayName("Caso enunciado: Máximo al inicio (98...)")
    void testEjemplo1() {
        String input = "987654321111111";
        // Buscamos 2 dígitos
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(98L, resultado);
    }

    @Test
    @DisplayName("Caso enunciado: Máximo en extremos (8...9)")
    void testEjemplo2() {
        String input = "811111111111119";
        // 8 al principio, 9 al final -> 89
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(89L, resultado);
    }

    @Test
    @DisplayName("Caso enunciado: Máximo al final (...78)")
    void testEjemplo3() {
        String input = "234234234234278";
        // 7 y 8 al final -> 78
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(78L, resultado);
    }

    @Test
    @DisplayName("Caso enunciado: Máximo intercalado (9...2)")
    void testEjemplo4() {
        String input = "818181911112111";
        // Encuentra 9 (mayor d1 posible) y luego 2 (mayor d2 restante) -> 92
        long resultado = EstrategiasBusqueda.Greedy(input, 2);
        assertEquals(92L, resultado);
    }

    @Test
    @DisplayName("Integración: Suma total de un controlador configurado para 2 dígitos")
    void testIntegracionFase1() {
        // Simulamos la lista de bancos
        List<BancoBaterias> bancos = List.of(
                new BancoBaterias("987654321"), // da 98
                new BancoBaterias("111111191")  // da 19 (1 al inicio, 9 al final)
        );

        // Configuramos la estrategia para 2 dígitos
        ProtocoloEnergia planA = s -> EstrategiasBusqueda.Greedy(s, 2);

        ControladorEscalera controlador = new ControladorEscalera(bancos, planA);

        // 98 + 19 = 117
        assertEquals(117L, controlador.activar());
    }
}