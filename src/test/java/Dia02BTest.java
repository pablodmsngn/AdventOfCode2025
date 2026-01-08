import software.ulpgc.aoc.dia02.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;


public class Dia02BTest {

    @Test
    @DisplayName("La estrategia B debe detectar patrones repetidos 2 veces (Retrocompatibilidad)")
    public void testEstrategiaBBase() {
        assertFalse(EstrategiasValidacion.PATRON_B.test(123123), "123123 debe ser inválido en Fase 2");
        assertFalse(EstrategiasValidacion.PATRON_B.test(11), "11 debe ser inválido en Fase 2");
    }

    @Test
    @DisplayName("La estrategia B debe detectar patrones repetidos 3 o más veces")
    public void testEstrategiaBAvanzada() {
        assertFalse(EstrategiasValidacion.PATRON_B.test(123123123), "123123123 (3 veces) debe ser inválido en Fase 2");
        assertFalse(EstrategiasValidacion.PATRON_B.test(111), "111 (3 veces) debe ser inválido en Fase 2");
        assertFalse(EstrategiasValidacion.PATRON_B.test(11111), "11111 (5 veces) debe ser inválido en Fase 2");
    }

    @Test
    @DisplayName("Ejemplo del enunciado Fase 2: Rango 95-115")
    public void testEjemploEnunciado() {
        RangoID rango = new RangoID(95, 115);
        long[] invalidos = rango.getInvalidIds(EstrategiasValidacion.PATRON_B).toArray();
        assertArrayEquals(new long[]{99, 111}, invalidos);
    }

    @Test
    @DisplayName("Motor debe sumar correctamente con la nueva estrategia")
    public void testMotorFase2() {
        Motor motor = new Motor(Stream.of(new RangoID(95, 115)), EstrategiasValidacion.PATRON_B);
        assertEquals(210, motor.run());
    }
}