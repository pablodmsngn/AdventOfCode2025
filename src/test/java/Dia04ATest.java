import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia04.CuadriculaAlmacen;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.a.SolucionadorImprentaA;
import software.ulpgc.aoc.dia04.b.SolucionadorImprentaB;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

        List<String> lineas = input.lines().toList();
        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(lineas);


        Ejecutador solucionador = new SolucionadorImprentaA(almacen);

        long resultado = solucionador.ejecutar();
        assertEquals(13, resultado);
    }

    @Test
    @DisplayName("Caso Borde: Mapa pequeño totalmente bloqueado")
    void testMapaBloqueado() {
        String input = """
                @@@
                @@@
                @@@
                """;
        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(input.lines().toList());
        Ejecutador solucionador = new SolucionadorImprentaA(almacen);

        assertEquals(4, solucionador.ejecutar());
    }
}