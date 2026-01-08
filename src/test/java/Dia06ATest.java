import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia06.CargadorEntrada;
import software.ulpgc.aoc.dia06.ControladorCompactador;
import software.ulpgc.aoc.dia06.Operacion;
import software.ulpgc.aoc.dia06.Operador;
import software.ulpgc.aoc.dia06.a.AnalizadorVertical;

import java.util.List;



import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Día 6 Parte A: Compactador Vertical")
public class Dia06ATest {

    @Test
    public void test_operacion() {
        // Probamos la lógica pura de la operación (Modelo)
        Operacion suma = new Operacion(List.of(1L, 4L, 5L), Operador.SUMAR);
        Operacion multi = new Operacion(List.of(1L, 2L, 5L), Operador.MULTIPLICAR);

        assertEquals(10L, suma.calcular());
        assertEquals(10L, multi.calcular());
    }

    @Test
    public void test_constructor_operaciones() {
        // Probamos el Builder (AnalizadorVertical) con datos manuales
        // Datos:
        // 45   64  387  23
        //  6   98  215 314
        //  * +    * +
        // Calc: 270, 162, 83205, 337
        long[] resultados = new AnalizadorVertical()
                .agregarLinea(" 45 64  387 23")
                .agregarLinea("  6 98  215 314")
                .agregarLinea("*   +   *   +")
                .construir()
                .mapToLong(Operacion::calcular)
                .toArray();

        assertArrayEquals(new long[] {270, 162, 83205, 337}, resultados);
    }

    @Test
    public void test_runner_con_fichero() {
        // Probamos la integración completa cargando el fichero real
        // Nota: Asegúrate de que 'Day06Input.txt' esté en resources
        ControladorCompactador controlador = CargadorEntrada.cargar("dia06input", new AnalizadorVertical());

        long resultado = controlador.ejecutar();

        // Sustituye este valor por el que te dio correcto en la Parte 1
        // Si usas el ejemplo del enunciado, sería 4277556
        assertEquals(4364617236318L, resultado);
    }
}