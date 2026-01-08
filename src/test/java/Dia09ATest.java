

import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia09.CargadorEntrada;
import software.ulpgc.aoc.dia09.ControladorCine;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dia09ATest {

    private final String INPUT_EJEMPLO = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
        """;

    @Test
    public void test_ejemplo_parte1() {
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));
        ControladorCine controlador = CargadorEntrada.cargar(inputStream);

        long resultado = controlador.obtenerAreaMaxima();

        // Según el enunciado: "el rectángulo más grande que puedes construir... tiene un área de 50"
        // (Entre 2,5 y 11,1 -> Ancho 10, Alto 5 -> 50)
        assertEquals(50L, resultado);
    }
}