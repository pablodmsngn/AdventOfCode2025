import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia12.CargadorEntrada;
import software.ulpgc.aoc.dia12.ControladorGranja;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dia12ATest {

    private final String INPUT_EJEMPLO = """
        0:
        ###
        ##.
        
        1:
        ###
        ##.
        .##
        
        2:
        .##
        ###
        ##.
        
        3:
        ##.
        ###
        ##.
        
        4:
        ###
        #..
        ###
        
        5:
        ###
        .#.
        ###
        
        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 0 1 0 3 2
        """;

    @Test
    public void testEjemploCompleto() {
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));
        ControladorGranja controlador = CargadorEntrada.cargar(inputStream);
        long resultado = controlador.contarRegionesValidas();
        assertEquals(2L, resultado, "El número de regiones válidas debería ser 2");
    }
}