import software.ulpgc.aoc.dia02.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Día 2 - Fase 2: Identificadores repetidos 2 o más veces")
public class Dia02BTest {

    @Test
    @DisplayName("La estrategia B debe detectar patrones repetidos 2 veces (Retrocompatibilidad)")
    void testEstrategiaBBase() {
        // Debe seguir detectando los mismos que la A
        assertFalse(EstrategiasValidacion.PATRON_B.test(123123), "123123 debe ser inválido en Fase 2");
        assertFalse(EstrategiasValidacion.PATRON_B.test(11), "11 debe ser inválido en Fase 2");
    }

    @Test
    @DisplayName("La estrategia B debe detectar patrones repetidos 3 o más veces")
    void testEstrategiaBAvanzada() {
        // NUEVO: Ahora estos también son inválidos (false)
        assertFalse(EstrategiasValidacion.PATRON_B.test(123123123), "123123123 (3 veces) debe ser inválido en Fase 2");
        assertFalse(EstrategiasValidacion.PATRON_B.test(111), "111 (3 veces) debe ser inválido en Fase 2");
        assertFalse(EstrategiasValidacion.PATRON_B.test(11111), "11111 (5 veces) debe ser inválido en Fase 2");
    }

    @Test
    @DisplayName("Ejemplo del enunciado Fase 2: Rango 95-115")
    void testEjemploEnunciado() {
        // Enunciado: "95-115 ahora tiene dos identificaciones no válidas: 99 y 111"
        RangoID rango = new RangoID(95, 115);

        long[] invalidos = rango.getInvalidIds(EstrategiasValidacion.PATRON_B).toArray();

        // Verificamos que encuentra el 99 y el 111
        assertArrayEquals(new long[]{99, 111}, invalidos);
    }

    @Test
    @DisplayName("Motor debe sumar correctamente con la nueva estrategia")
    void testMotorFase2() {
        // Rango 95-115. Inválidos: 99, 111. Suma = 210.
        Motor motor = new Motor(Stream.of(new RangoID(95, 115)), EstrategiasValidacion.PATRON_B);

        assertEquals(210, motor.run());
    }
}