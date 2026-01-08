
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
        // Un número de 12 dígitos NO cabe en un int.
        // Si el código usa int internamente, esto fallará o dará negativo.
        String input = "999999999999"; // 12 nueves

        long esperado = 999999999999L;
        long resultado = EstrategiasBusqueda.Greedy(input, 12);

        assertEquals(esperado, resultado, "El algoritmo debe retornar un Long válido sin desbordarse");
    }

    @Test
    @DisplayName("Validar lógica Greedy con 12 dígitos")
    public void testLogicaGreedy12() {
        // Cadena: Un '1' seguido de doce '9's. Total 13 caracteres.
        // Input: "1999999999999"

        // Comportamiento incorrecto: Coger el '1' inicial y luego once '9's -> 199.999.999.999
        // Comportamiento correcto (Greedy): Saltar el '1', coger el primer '9' y el resto -> 999.999.999.999
        String input = "1999999999999";

        long esperado = 999999999999L;
        long resultado = EstrategiasBusqueda.Greedy(input, 12);

        assertEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Integración: Suma total de un controlador configurado para 12 dígitos")
    public void testIntegracionFase2() {
        List<BancoBaterias> bancos = List.of(
                new BancoBaterias("999999999999"), // 12 nueves
                new BancoBaterias("888888888888")  // 12 ochos
        );

        // Configuramos la estrategia para 12 dígitos
        ProtocoloEnergia planB = s -> EstrategiasBusqueda.Greedy(s, 12);

        ControladorEscalera controlador = new ControladorEscalera(bancos, planB);

        long esperado = 999999999999L + 888888888888L; // 1888888888887

        assertEquals(esperado, controlador.activar());
    }
}