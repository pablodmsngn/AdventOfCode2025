package software.ulpgc.aoc.dia10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia10ATest {

    private final String INPUT_EJEMPLO = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """;

    @Test
    @DisplayName("Verificar ejemplo: Total de pulsaciones mínimas (Luces) es 7")
    public void testEjemploParte1() {
        // 1. Cargamos el input de ejemplo en memoria
        var inputStream = new ByteArrayInputStream(INPUT_EJEMPLO.getBytes(StandardCharsets.UTF_8));

        // 2. Inicializamos el controlador
        ControladorFabrica controlador = CargadorEntrada.cargar(inputStream);

        // 3. Ejecutamos la lógica de la Parte 1
        long resultado = controlador.ejecutarParte1();

        // 4. Verificamos (2 + 3 + 2 = 7)
        assertEquals(7L, resultado, "El total de pulsaciones para configurar las luces debería ser 7");
    }
}