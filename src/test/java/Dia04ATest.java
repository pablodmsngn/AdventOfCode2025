import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.SolucionadorImprentaA;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dia04ATest {

    @Test
    @DisplayName("Caso del Enunciado: Debe detectar 13 rollos accesibles")
    void testEjemploEnunciado() {
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

        // Usamos SolucionadorImprentaA para la Parte 1
        Ejecutador solucionador = new SolucionadorImprentaA(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        long resultado = solucionador.ejecutar();

        assertEquals(13, resultado);
    }

    @Test
    @DisplayName("Caso Borde: Mapa pequeño totalmente bloqueado")
    void testMapaBloqueado() {
        // El centro (1,1) está rodeado por 8 rollos -> Bloqueado.
        String input = """
                @@@
                @@@
                @@@
                """;

        Ejecutador solucionador = new SolucionadorImprentaA(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        // 9 rollos en total - 1 bloqueado en el centro = 8 accesibles
        assertEquals(4, solucionador.ejecutar());
    }
}