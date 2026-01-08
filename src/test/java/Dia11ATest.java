import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia11.CargadorEntrada;
import software.ulpgc.aoc.dia11.ControladorReactor;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dia11ATest {

    private final String INPUT_EJEMPLO = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
        """;

    @Test
    @DisplayName("Ejemplo Parte 1: Verificar 5 rutas")
    public void testEjemplo() {
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));
        ControladorReactor controlador = CargadorEntrada.cargar(inputStream);

        long resultado = controlador.contarRutasTotales();

        // El enunciado lista 5 rutas posibles
        assertEquals(5L, resultado);
    }
}