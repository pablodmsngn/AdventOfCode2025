import software.ulpgc.aoc.dia02.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Día 2 - Fase 1: Identificadores repetidos exactamente 2 veces")
public class Dia02ATest {

    @Test
    @DisplayName("La estrategia A debe detectar patrones repetidos exactamente 2 veces")
    void testEstrategiaA() {
        // IDs Inválidos (Cumplen el patrón repetido 2 veces) -> La validación retorna FALSE (porque falla la regla de tienda)
        // negate() en el código invierte esto, así que aquí probamos el predicado directo.

        // 123123 -> Repetido 2 veces. Es un "ID Inválido" para la tienda.
        // El predicado devuelve FALSE si coincide con el patrón "malo".
        assertFalse(EstrategiasValidacion.PATRON_A.test(123123), "123123 debe ser detectado como inválido (false)");
        assertFalse(EstrategiasValidacion.PATRON_A.test(11), "11 debe ser detectado como inválido");
        assertFalse(EstrategiasValidacion.PATRON_A.test(222222), "222222 (222-222) debe ser detectado como inválido");
    }

    @Test
    @DisplayName("La estrategia A debe ignorar patrones repetidos 3 o más veces")
    void testEstrategiaAIgnoraMultiples() {
        // 123123123 -> 3 veces. Para la Fase 1, esto es un ID válido (TRUE).
        assertTrue(EstrategiasValidacion.PATRON_A.test(123123123), "123123123 debería ser válido en Fase 1");

        // 111 -> 3 veces '1'. Válido en Fase 1.
        assertTrue(EstrategiasValidacion.PATRON_A.test(111), "111 debería ser válido en Fase 1");
    }

    @Test
    @DisplayName("RangoID debe filtrar correctamente los IDs inválidos")
    void testRangoID() {
        // Rango 10-12.
        // 10: Válido
        // 11: Inválido (1-1)
        // 12: Válido
        RangoID rango = new RangoID(10, 12);

        // getInvalidIds usa negate(), así que nos devolverá los que dieron FALSE en el test anterior (los repetidos)
        long[] invalidos = rango.getInvalidIds(EstrategiasValidacion.PATRON_A).toArray();

        assertArrayEquals(new long[]{11}, invalidos);
    }

    @Test
    @DisplayName("Motor debe sumar correctamente los IDs inválidos")
    void testMotorIntegracion() {
        // Creamos un stream de rangos simulado
        RangoID rango1 = new RangoID("10-12"); // Contiene 11 (Inválido)
        RangoID rango2 = new RangoID("20-22"); // Contiene 22 (Inválido)

        Motor motor = new Motor(Stream.of(rango1, rango2), EstrategiasValidacion.PATRON_A);

        // Suma esperada: 11 + 22 = 33
        assertEquals(33, motor.run());
    }
}