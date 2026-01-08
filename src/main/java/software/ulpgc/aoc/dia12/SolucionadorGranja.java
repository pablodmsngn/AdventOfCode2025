package software.ulpgc.aoc.dia12;

import java.util.*;

public class SolucionadorGranja {

    public boolean resolver(int ancho, int alto, List<Forma> piezas) {
        // 1. Poda rápida por área: Si los regalos ocupan más que el tablero, es imposible.
        int areaTotal = piezas.stream().mapToInt(Forma::area).sum();
        if (areaTotal > ancho * alto) return false;

        // 2. Ordenar piezas: Más grandes primero (Heurística Fail-Fast)
        List<Forma> piezasOrdenadas = new ArrayList<>(piezas);
        piezasOrdenadas.sort((a, b) -> {
            int cmp = Integer.compare(b.area(), a.area());
            // Si tienen misma área, agrupar por ID para que la simetría funcione mejor
            return cmp != 0 ? cmp : Integer.compare(a.id(), b.id());
        });

        // 3. Seleccionar estrategia según tamaño del tablero
        if (ancho * alto <= 64) {
            return resolverLong(ancho, alto, piezasOrdenadas);
        } else {
            return resolverBitSet(ancho, alto, piezasOrdenadas);
        }
    }

    // --- ESTRATEGIA A: PRIMITIVOS (Velocidad Máxima) ---
    private boolean resolverLong(int ancho, int alto, List<Forma> piezas) {
        // Cache: ID de Forma -> Array de máscaras posibles (long[])
        Map<Integer, long[]> cacheMascaras = new HashMap<>();

        // Pre-calcular máscaras solo UNA VEZ por tipo de pieza (ID)
        for (Forma p : piezas) {
            if (!cacheMascaras.containsKey(p.id())) {
                List<Long> opciones = generarOpcionesLong(p, ancho, alto);
                if (opciones.isEmpty()) return false; // La pieza no cabe
                cacheMascaras.put(p.id(), opciones.stream().mapToLong(l -> l).toArray());
            }
        }

        return recursividadLong(0, 0L, piezas, cacheMascaras, -1);
    }

    private boolean recursividadLong(int idx, long tablero, List<Forma> piezas,
                                     Map<Integer, long[]> cacheMascaras, int lastOptionIndex) {
        if (idx == piezas.size()) return true;

        Forma piezaActual = piezas.get(idx);
        long[] opciones = cacheMascaras.get(piezaActual.id());

        // ROTURA DE SIMETRÍA:
        // Si la pieza actual es idéntica a la anterior (mismo ID), forzamos que se coloque
        // en una posición posterior (índice mayor) en la lista de opciones.
        boolean esIgualAnterior = (idx > 0) && (piezaActual.id() == piezas.get(idx - 1).id());
        int inicio = esIgualAnterior ? lastOptionIndex + 1 : 0;

        for (int i = inicio; i < opciones.length; i++) {
            long opcion = opciones[i];

            // Comprobación de colisión: (Tablero & Mascara) == 0
            if ((tablero & opcion) == 0) {
                // Colocar (OR) y Recursión
                if (recursividadLong(idx + 1, tablero | opcion, piezas, cacheMascaras, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Long> generarOpcionesLong(Forma pieza, int ancho, int alto) {
        List<Long> opciones = new ArrayList<>();
        // generarVariaciones ya maneja la unicidad geométrica (HashSet interno)
        for (Forma var : pieza.generarVariaciones()) {
            for (int r = 0; r < alto; r++) {
                for (int c = 0; c < ancho; c++) {
                    long mask = 0L;
                    boolean valido = true;
                    for (Coordenada p : var.puntos()) {
                        int nr = r + p.r();
                        int nc = c + p.c();
                        if (nr < 0 || nr >= alto || nc < 0 || nc >= ancho) {
                            valido = false; break;
                        }
                        mask |= (1L << (nr * ancho + nc));
                    }
                    if (valido) opciones.add(mask);
                }
            }
        }
        return opciones;
    }

    // --- ESTRATEGIA B: BITSET (Para tableros gigantes > 64 casillas) ---
    private boolean resolverBitSet(int ancho, int alto, List<Forma> piezas) {
        Map<Integer, List<BitSet>> cacheMascaras = new HashMap<>();

        for (Forma p : piezas) {
            if (!cacheMascaras.containsKey(p.id())) {
                List<BitSet> opciones = generarOpcionesBitSet(p, ancho, alto);
                if (opciones.isEmpty()) return false;
                cacheMascaras.put(p.id(), opciones);
            }
        }

        return recursividadBitSet(0, new BitSet(ancho * alto), piezas, cacheMascaras, -1);
    }

    private boolean recursividadBitSet(int idx, BitSet tablero, List<Forma> piezas,
                                       Map<Integer, List<BitSet>> cacheMascaras, int lastOptionIndex) {
        if (idx == piezas.size()) return true;

        Forma piezaActual = piezas.get(idx);
        List<BitSet> opciones = cacheMascaras.get(piezaActual.id());

        boolean esIgualAnterior = (idx > 0) && (piezaActual.id() == piezas.get(idx - 1).id());
        int inicio = esIgualAnterior ? lastOptionIndex + 1 : 0;

        for (int i = inicio; i < opciones.size(); i++) {
            BitSet opcion = opciones.get(i);
            if (!tablero.intersects(opcion)) {
                tablero.or(opcion);
                if (recursividadBitSet(idx + 1, tablero, piezas, cacheMascaras, i)) return true;
                tablero.xor(opcion); // Backtrack
            }
        }
        return false;
    }

    private List<BitSet> generarOpcionesBitSet(Forma pieza, int ancho, int alto) {
        List<BitSet> opciones = new ArrayList<>();
        for (Forma var : pieza.generarVariaciones()) {
            for (int r = 0; r < alto; r++) {
                for (int c = 0; c < ancho; c++) {
                    BitSet bs = new BitSet(ancho * alto);
                    boolean valido = true;
                    for (Coordenada p : var.puntos()) {
                        int nr = r + p.r();
                        int nc = c + p.c();
                        if (nr < 0 || nr >= alto || nc < 0 || nc >= ancho) {
                            valido = false; break;
                        }
                        bs.set(nr * ancho + nc);
                    }
                    if (valido) opciones.add(bs);
                }
            }
        }
        return opciones;
    }
}