import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia01.CajaFuerte;
import software.ulpgc.aoc.dia01.ProtocolosSeguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dia01BTest {

    @Test
    public void testMultiplesVueltas() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_B);
        caja.rotar("R1000");
        assertEquals(10, caja.getVecesCero());
    }

    @Test
    public void testCruceDerecha() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_B);
        caja.rotar("R40");
        assertEquals(0, caja.getVecesCero());
        caja.rotar("R20");
        assertEquals(1, caja.getVecesCero());
    }

    @Test
    public void testCruceIzquierda() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_B);
        caja.rotar("L40");
        assertEquals(0, caja.getVecesCero());
        caja.rotar("L20");
        assertEquals(1, caja.getVecesCero());
    }

    @Test
    public void testAterrizajeExactoEnCero() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_B);
        caja.rotar("R50");
        assertEquals(1, caja.getVecesCero());
    }

    @Test
    public void testAceptacionParteB() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_B);
        String[] entradas = {
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        };

        for (String entrada : entradas) caja.rotar(entrada);
        assertEquals(6, caja.getVecesCero());
    }
}