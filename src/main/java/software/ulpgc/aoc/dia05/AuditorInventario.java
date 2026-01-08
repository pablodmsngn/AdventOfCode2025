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


    /**
     * Parte 2: Calcula cuántos IDs únicos existen dentro de la unión de todos los rangos.
     * Algoritmo: Merge Intervals (O(N log N)).
     */

    public long calcularCoberturaTotal() {
        if (rangos.isEmpty()) return 0;

        // 1. Crear una copia y ordenar por el inicio del rango (ej: 3-5, 10-14, 12-18...)
        List<Rango> ordenados = new ArrayList<>(this.rangos);
        Collections.sort(ordenados);

        List<Rango> fusionados = new ArrayList<>();
        Rango actual = ordenados.get(0);

        // 2. Iterar y fusionar solapamientos
        for (int i = 1; i < ordenados.size(); i++) {
            Rango siguiente = ordenados.get(i);

            if (actual.solapaCon(siguiente)) {
                // Si se tocan, extendemos el rango 'actual' para abarcar al 'siguiente'
                actual = actual.fusionar(siguiente);
            } else {
                // Si no se tocan, el 'actual' está cerrado. Lo guardamos y pasamos al siguiente.
                fusionados.add(actual);
                actual = siguiente;
            }
        }
        // Añadir el último rango que quedó pendiente
        fusionados.add(actual);
        // 3. Sumar las longitudes de los rangos ya limpios y sin solapamientos
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
