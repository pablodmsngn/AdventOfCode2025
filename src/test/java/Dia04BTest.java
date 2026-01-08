import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.SolucionadorImprentaB;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia04BTest {

    @Test
    @DisplayName("Caso del Enunciado: Simulación completa (Resultado esperado: 43)")
    void testSimulacionCompleta() {
        String input = """
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.
                """;


        Ejecutador solucionador = new SolucionadorImprentaB(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        long resultado = solucionador.ejecutar();

        assertEquals(43, resultado);
    }

    @Test
    @DisplayName("Caso Lógico: Desbloqueo Progresivo")
    void testDesbloqueoProgresivo() {

        String input = """
                .@.
                @@@
                .@.
                """;

        Ejecutador solucionador = new SolucionadorImprentaB(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        assertEquals(5, solucionador.ejecutar());
    }
}