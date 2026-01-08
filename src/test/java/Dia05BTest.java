import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia05.AuditorInventario;
import software.ulpgc.aoc.dia05.Rango;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class Dia05BTest {

    @Test
    @DisplayName("Rango: Detectar solapamientos entre intervalos")
    public void testRangoSolapamiento() {
        Rango base = new Rango(10, 20);

        assertAll("Interacciones entre rangos",
                () -> assertTrue(base.solapaCon(new Rango(5, 15)), "Solapa izquierda (5-15)"),
                () -> assertTrue(base.solapaCon(new Rango(15, 25)), "Solapa derecha (15-25)"),
                () -> assertTrue(base.solapaCon(new Rango(12, 18)), "Solapa interna (12-18)"),
                () -> assertFalse(base.solapaCon(new Rango(1, 9)), "No solapa (está antes)"),
                () -> assertFalse(base.solapaCon(new Rango(21, 30)), "No solapa (está después)")
        );
    }

    @Test
    @DisplayName("Rango: Fusionar dos rangos solapados")
    public void testRangoFusion() {
        Rango r1 = new Rango(10, 15);
        Rango r2 = new Rango(12, 18);
        Rango resultado = r1.fusionar(r2);
        assertEquals(10, resultado.min(), "El inicio debe ser el menor de los dos");
        assertEquals(18, resultado.max(), "El final debe ser el mayor de los dos");
        assertEquals(9, resultado.longitud(), "La longitud total debe ser 9");
    }

    @Test
    @DisplayName("Auditor: Cobertura simple (Rangos disjuntos)")
    public void testCoberturaSinSolapamiento() {
        List<Rango> rangos = new ArrayList<>();
        rangos.add(new Rango(1, 2));
        rangos.add(new Rango(4, 5));
        AuditorInventario auditor = new AuditorInventario(rangos, null, null);
        assertEquals(4, auditor.calcularCoberturaTotal());
    }

    @Test
    @DisplayName("Auditor: Cobertura compleja (Con fusión)")
    public void testCoberturaConSolapamiento() {
        List<Rango> rangos = new ArrayList<>();
        rangos.add(new Rango(1, 5));
        rangos.add(new Rango(4, 8));
        rangos.add(new Rango(10, 10));
        AuditorInventario auditor = new AuditorInventario(rangos, null, null);
        assertEquals(9, auditor.calcularCoberturaTotal(), "El algoritmo debe fusionar antes de sumar");
    }
}