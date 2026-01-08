import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.dia05.AuditorInventario;
import software.ulpgc.aoc.dia05.ProtocoloFrescura;
import software.ulpgc.aoc.dia05.Rango;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class Dia05ATest {



    @Test
    @DisplayName("Rango: Verificar si un número está dentro")
    public void testRangoContiene() {
        Rango rango = new Rango(10, 20);
        assertAll("Límites y valores internos",
                () -> assertTrue(rango.contiene(10), "Debe incluir el límite inferior"),
                () -> assertTrue(rango.contiene(20), "Debe incluir el límite superior"),
                () -> assertTrue(rango.contiene(15), "Debe incluir valores intermedios"),
                () -> assertFalse(rango.contiene(9), "No debe incluir valores menores"),
                () -> assertFalse(rango.contiene(21), "No debe incluir valores mayores")
        );
    }


    @Test
    @DisplayName("Auditor: Contar ingredientes frescos usando estrategia")
    public void testAuditoriaParte1() {

        List<Rango> rangos = List.of(new Rango(5, 10));
        List<Long> ids = List.of(1L, 5L, 8L, 11L);
        ProtocoloFrescura protocolo = (id, listaRangos) ->
                listaRangos.stream().anyMatch(r -> r.contiene(id));
        AuditorInventario auditor = new AuditorInventario(rangos, ids, protocolo);
        long resultado = auditor.auditar();
        assertEquals(2, resultado, "Debería encontrar exactamente 2 ingredientes frescos (5 y 8)");
    }
}
