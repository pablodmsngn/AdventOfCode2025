import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia01.CajaFuerte;
import software.ulpgc.aoc.dia01.ProtocolosSeguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dia01ATest {

    @Test
    public void testRobustez() {

        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_A);
        caja.rotar(null);
        caja.rotar("");
        caja.rotar("   ");
        assertEquals(0, caja.getVecesCero());
    }

    @Test
    public void testEstadoInicial() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_A);
        assertEquals(0, caja.getVecesCero());
    }

    @Test
    public void testParadaEnCero() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_A);
        caja.rotar("R50");
        assertEquals(1, caja.getVecesCero());
    }

    @Test
    public void testPasoPorCeroIgnorado() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_A);
        caja.rotar("R60");
        assertEquals(0, caja.getVecesCero());
    }

    @Test
    public void testAceptacionParteA() {
        CajaFuerte caja = new CajaFuerte(ProtocolosSeguridad.PART_A);
        String[] entradas = {
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        };
        for (String entrada : entradas) caja.rotar(entrada);
        assertEquals(3, caja.getVecesCero());
    }
}