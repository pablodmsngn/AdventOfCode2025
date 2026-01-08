
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia09.CargadorEntrada;
import software.ulpgc.aoc.dia09.ControladorCine;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia09BTest {

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
    @DisplayName("Ejemplo: Área máxima permitida es 24")
    public void test_ejemplo_parte2() {
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));
        ControladorCine controlador = CargadorEntrada.cargar(inputStream);

        long resultado = controlador.obtenerAreaMaximaPermitida();

        // El rectángulo válido más grande es entre 9,5 y 2,3 con área 24
        assertEquals(24L, resultado);
    }
}