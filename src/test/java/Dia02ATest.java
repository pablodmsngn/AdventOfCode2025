import org.junit.jupiter.api.DisplayName;
import software.ulpgc.aoc.dia02.*;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;


public class Dia02ATest {

    @Test
    @DisplayName("La estrategia A debe detectar patrones repetidos exactamente 2 veces")
    public void testEstrategiaA() {
        assertFalse(EstrategiasValidacion.PATRON_A.test(123123), "123123 debe ser detectado como inválido (false)");
        assertFalse(EstrategiasValidacion.PATRON_A.test(11), "11 debe ser detectado como inválido");
        assertFalse(EstrategiasValidacion.PATRON_A.test(222222), "222222 (222-222) debe ser detectado como inválido");
    }

    @Test
    @DisplayName("La estrategia A debe ignorar patrones repetidos 3 o más veces")
    public void testEstrategiaAIgnoraMultiples() {
        assertTrue(EstrategiasValidacion.PATRON_A.test(123123123), "123123123 debería ser válido en Fase 1");
        assertTrue(EstrategiasValidacion.PATRON_A.test(111), "111 debería ser válido en Fase 1");
    }

    @Test
    @DisplayName("RangoID debe filtrar correctamente los IDs inválidos")
    public void testRangoID() {
        RangoID rango = new RangoID(10, 12);
        long[] invalidos = rango.getInvalidIds(EstrategiasValidacion.PATRON_A).toArray();
        assertArrayEquals(new long[]{11}, invalidos);
    }

    @Test
    @DisplayName("Motor debe sumar correctamente los IDs inválidos")
    public void testMotorIntegracion() {
        RangoID rango1 = new RangoID("10-12");
        RangoID rango2 = new RangoID("20-22");
        Motor motor = new Motor(Stream.of(rango1, rango2), EstrategiasValidacion.PATRON_A);
        assertEquals(33, motor.run());
    }
}