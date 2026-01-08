import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia06.CargadorEntrada;
import software.ulpgc.aoc.dia06.ControladorCompactador;
import software.ulpgc.aoc.dia06.Operacion;
import software.ulpgc.aoc.dia06.b.AnalizadorCefalopodo;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class Dia06BTest {

    @Test
    public void test_operation_builder() {
        long[] results = new AnalizadorCefalopodo()
                .agregarLinea(" 45 64  387 23")
                .agregarLinea("  6 98  215 314")
                .agregarLinea("*   +   *   +")
                .construir()
                .mapToLong(Operacion::calcular)
                .toArray();
        assertArrayEquals(new long[] {224, 117, 194400, 58}, results);
    }

    @Test
    public void test_runner_with_inputs() {
        ControladorCompactador controlador = CargadorEntrada.cargar("dia06input", new AnalizadorCefalopodo());
        long result = controlador.ejecutar();
        assertEquals(9077004354241L, result);
    }
}