package software.ulpgc.aoc.dia12;

import java.util.*;

public class SolucionadorGranja {

    public boolean resolver(Region region, List<Forma> piezas) {
        if (piezas.stream().mapToInt(Forma::area).sum() > region.area()) {
            return false;
        }

        List<Forma> piezasOrdenadas = new ArrayList<>(piezas);
        piezasOrdenadas.sort(Comparator.comparingInt(Forma::area).reversed()
                .thenComparingInt(Forma::id));

        if (region.esPequena()) {
            return resolverConPrimitivos(region, piezasOrdenadas);
        } else {
            return resolverConBitSet(region, piezasOrdenadas);
        }
    }

    private boolean resolverConPrimitivos(Region region, List<Forma> piezas) {
        Map<Integer, long[]> cache = precalcularMascarasLong(region, piezas);
        if (cache.size() != piezas.stream().map(Forma::id).distinct().count()) return false;

        return backtrackLong(0, 0L, piezas, cache, -1);
    }

    private boolean backtrackLong(int idx, long tablero, List<Forma> piezas, Map<Integer, long[]> cache, int lastOptionIndex) {
        if (idx == piezas.size()) return true;

        Forma pieza = piezas.get(idx);
        long[] opciones = cache.get(pieza.id());

        boolean esIgual = (idx > 0) && (pieza.id() == piezas.get(idx - 1).id());
        int inicio = esIgual ? lastOptionIndex + 1 : 0;

        for (int i = inicio; i < opciones.length; i++) {
            if ((tablero & opciones[i]) == 0) {
                if (backtrackLong(idx + 1, tablero | opciones[i], piezas, cache, i)) return true;
            }
        }
        return false;
    }

    private boolean resolverConBitSet(Region region, List<Forma> piezas) {
        Map<Integer, List<BitSet>> cache = precalcularMascarasBitSet(region, piezas);
        if (cache.size() != piezas.stream().map(Forma::id).distinct().count()) return false;

        return backtrackBitSet(0, new BitSet(region.area()), piezas, cache, -1);
    }

    private boolean backtrackBitSet(int idx, BitSet tablero, List<Forma> piezas, Map<Integer, List<BitSet>> cache, int lastOptionIndex) {
        if (idx == piezas.size()) return true;

        Forma pieza = piezas.get(idx);
        List<BitSet> opciones = cache.get(pieza.id());

        boolean esIgual = (idx > 0) && (pieza.id() == piezas.get(idx - 1).id());
        int inicio = esIgual ? lastOptionIndex + 1 : 0;

        for (int i = inicio; i < opciones.size(); i++) {
            BitSet opcion = opciones.get(i);
            if (!tablero.intersects(opcion)) {
                tablero.or(opcion);
                if (backtrackBitSet(idx + 1, tablero, piezas, cache, i)) return true;
                tablero.xor(opcion);
            }
        }
        return false;
    }

    private Map<Integer, long[]> precalcularMascarasLong(Region region, List<Forma> piezas) {
        Map<Integer, long[]> cache = new HashMap<>();
        for (Forma p : piezas) {
            if (!cache.containsKey(p.id())) {
                List<Long> opts = new ArrayList<>();
                generarOpciones(p, region, (r, c, pts) -> {
                    long mask = 0L;
                    for (Coordenada pt : pts) mask |= (1L << (pt.r() * region.ancho() + pt.c()));
                    opts.add(mask);
                });
                if (!opts.isEmpty()) cache.put(p.id(), opts.stream().mapToLong(l -> l).toArray());
            }
        }
        return cache;
    }

    private Map<Integer, List<BitSet>> precalcularMascarasBitSet(Region region, List<Forma> piezas) {
        Map<Integer, List<BitSet>> cache = new HashMap<>();
        for (Forma p : piezas) {
            if (!cache.containsKey(p.id())) {
                List<BitSet> opts = new ArrayList<>();
                generarOpciones(p, region, (r, c, pts) -> {
                    BitSet bs = new BitSet(region.area());
                    for (Coordenada pt : pts) bs.set(pt.r() * region.ancho() + pt.c());
                    opts.add(bs);
                });
                if (!opts.isEmpty()) cache.put(p.id(), opts);
            }
        }
        return cache;
    }


    interface MascaraConsumer {
        void accept(int r, int c, List<Coordenada> puntosTransladados);
    }

    private void generarOpciones(Forma pieza, Region region, MascaraConsumer consumer) {
        for (Forma var : pieza.generarVariaciones()) {
            for (int r = 0; r < region.alto(); r++) {
                for (int c = 0; c < region.ancho(); c++) {
                    List<Coordenada> transladados = new ArrayList<>();
                    boolean valido = true;
                    for (Coordenada p : var.puntos()) {
                        int nr = r + p.r();
                        int nc = c + p.c();
                        if (nr < 0 || nr >= region.alto() || nc < 0 || nc >= region.ancho()) {
                            valido = false; break;
                        }
                        transladados.add(new Coordenada(nr, nc));
                    }
                    if (valido) consumer.accept(r, c, transladados);
                }
            }
        }
    }
}