package software.ulpgc.aoc.dia05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditorInventario {
    private final List<Rango> rangos;
    private final List<Long> idsDisponibles;
    private final ProtocoloFrescura protocolo;

    public AuditorInventario(List<Rango> rangos, List<Long> ids, ProtocoloFrescura protocolo) {
        this.rangos = rangos;
        this.idsDisponibles = ids;
        this.protocolo = protocolo;
    }


    public long calcularCoberturaTotal() {
        if (rangos.isEmpty()) return 0;
        List<Rango> ordenados = new ArrayList<>(this.rangos);
        Collections.sort(ordenados);
        List<Rango> fusionados = new ArrayList<>();
        Rango actual = ordenados.get(0);
        for (int i = 1; i < ordenados.size(); i++) {
            Rango siguiente = ordenados.get(i);
            if (actual.solapaCon(siguiente)) {
                actual = actual.fusionar(siguiente);
            } else {
                fusionados.add(actual);
                actual = siguiente;
            }
        }
        fusionados.add(actual);
        return fusionados.stream()
                .mapToLong(Rango::longitud)
                .sum();
    }

    public long auditar() {
        return idsDisponibles.stream()
                .filter(id -> protocolo.esFresco(id, rangos))
                .count();
    }
}
