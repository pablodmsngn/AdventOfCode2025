

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia08.CargadorEntrada;
import software.ulpgc.aoc.dia08.ControladorLuces;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dia08BTest {

    private final String INPUT_EJEMPLO = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
        """;

    @Test
    @DisplayName("Verificar ejemplo: 216 * 117 = 25272")
    public void test_ejemplo_unificacion() {
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));
        ControladorLuces controlador = CargadorEntrada.cargar(inputStream);

        long resultado = controlador.ejecutarUnificacion();

        // Según el enunciado: "multiplicar las coordenadas X de esas dos cajas de conexiones (216 y 117) da como resultado 25272"
        assertEquals(25272L, resultado);
    }
}