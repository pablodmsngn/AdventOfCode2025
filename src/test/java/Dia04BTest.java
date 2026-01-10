import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia04.CuadriculaAlmacen;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.b.SolucionadorImprentaB;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

        List<String> lineas = input.lines().toList();
        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(lineas);


        Ejecutador solucionador = new SolucionadorImprentaB(almacen);

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

        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(input.lines().toList());


        Ejecutador solucionador = new SolucionadorImprentaB(almacen);

        assertEquals(5, solucionador.ejecutar());
    }
}