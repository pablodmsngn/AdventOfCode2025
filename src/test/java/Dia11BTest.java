
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia11.CargadorEntrada;
import software.ulpgc.aoc.dia11.ControladorReactor;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia11BTest {

    private final String INPUT_EJEMPLO = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
        """;

    @Test
    public void testEjemploParte2() {
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));
        ControladorReactor controlador = CargadorEntrada.cargar(inputStream);

        long resultado = controlador.contarRutasCriticas();
        assertEquals(2L, resultado);
    }
}