package software.ulpgc.aoc.dia10;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Maquina(Indicador objetivo, List<Boton> botones) {

    // Parte 1
    public int resolverPulsacionesMinimas() {
        List<Set<Boton>> soluciones = buscarSolucionesLuces(objetivo);
        return soluciones.isEmpty() ? 0 : soluciones.get(0).size();
    }

    // Parte 2
    public int resolverRequisitosVoltaje(Map<Indicador, Integer> cache) {
        if (cache.containsKey(objetivo)) return cache.get(objetivo);

        if (objetivo.voltajes().stream().allMatch(v -> v == 0)) {
            return 0;
        }

        Indicador indicadorParidad = convertirVoltajeALuces(objetivo);
        Maquina maquinaIntermedia = new Maquina(indicadorParidad, botones);
        List<Set<Boton>> solucionesParidad = maquinaIntermedia.buscarSolucionesLuces(indicadorParidad);

        int minCoste = 99999;

        for (Set<Boton> solucion : solucionesParidad) {
            Indicador efecto = aplicarBotones(indicadorParidad.crearEstadoInicial(), solucion);
            Indicador resto = objetivo.reducirVoltajesCon(efecto);

            if (resto.voltajes().stream().anyMatch(v -> v < 0)) continue;

            Indicador nuevoObjetivo = resto.mitadVoltajes();

            Maquina siguienteMaquina = new Maquina(nuevoObjetivo, botones);
            int costeRecursivo = siguienteMaquina.resolverRequisitosVoltaje(cache);

            if (costeRecursivo < 99999) {
                int costeTotal = solucion.size() + 2 * costeRecursivo;
                if (costeTotal < minCoste) {
                    minCoste = costeTotal;
                }
            }
        }

        cache.put(objetivo, minCoste);
        return minCoste;
    }


    private List<Set<Boton>> buscarSolucionesLuces(Indicador target) {
        List<Set<Boton>> soluciones = new ArrayList<>();
        Set<Long> visitados = new HashSet<>();
        Queue<Long> cola = new ArrayDeque<>();

        cola.add(0L);
        visitados.add(0L);

        Indicador base = target.crearEstadoInicial();
        List<Estado> targetEstados = target.estados();

        while (!cola.isEmpty()) {
            long mask = cola.poll();

            if (calcularSoloEstados(base, mask).equals(targetEstados)) {
                soluciones.add(convertirMascaraASet(mask));
            }

            for (int i = 0; i < botones.size(); i++) {
                if ((mask & (1L << i)) != 0) continue;

                long nuevaMask = mask | (1L << i);

                if (visitados.add(nuevaMask)) {
                    cola.add(nuevaMask);
                }
            }
        }
        return soluciones;
    }

    private List<Estado> calcularSoloEstados(Indicador base, long mask) {
        return IntStream.range(0, base.estados().size())
                .mapToObj(i -> {
                    boolean encendidoInicial = (base.estados().get(i) == Estado.ENCENDIDO);
                    long toggleCount = 0;
                    for (int b = 0; b < botones.size(); b++) {
                        if ((mask & (1L << b)) != 0) { // Si el botón 'b' está pulsado
                            if (botones.get(b).indicesAfectados().contains(i)) {
                                toggleCount++;
                            }
                        }
                    }
                    boolean cambio = (toggleCount % 2 != 0);
                    boolean resultadoEncendido = cambio ? !encendidoInicial : encendidoInicial;
                    return resultadoEncendido ? Estado.ENCENDIDO : Estado.APAGADO;
                })
                .toList();
    }

    private Set<Boton> convertirMascaraASet(long mask) {
        Set<Boton> set = new HashSet<>();
        for (int i = 0; i < botones.size(); i++) {
            if ((mask & (1L << i)) != 0) {
                set.add(botones.get(i));
            }
        }
        return set;
    }

    private Indicador convertirVoltajeALuces(Indicador ind) {
        List<Estado> nuevosEstados = IntStream.range(0, ind.estados().size())
                .mapToObj(i -> ind.voltajes().get(i) % 2 != 0 ? Estado.ENCENDIDO : Estado.APAGADO)
                .toList();
        return new Indicador(nuevosEstados, ind.crearEstadoInicial().voltajes());
    }

    private Indicador aplicarBotones(Indicador estadoActual, Set<Boton> botones) {
        return botones.stream().reduce(estadoActual, this::aplicarBoton, (a, b) -> b);
    }

    private Indicador aplicarBoton(Indicador estado, Boton boton) {
        List<Estado> nuevosEstados = IntStream.range(0, estado.estados().size())
                .mapToObj(i -> boton.indicesAfectados().contains(i)
                        ? estado.alternarEstado(i)
                        : estado.estados().get(i))
                .toList();

        List<Integer> nuevosVoltajes = IntStream.range(0, estado.voltajes().size())
                .mapToObj(i -> estado.voltajes().get(i) + (boton.indicesAfectados().contains(i) ? 1 : 0))
                .toList();

        return new Indicador(nuevosEstados, nuevosVoltajes);
    }

    public static Maquina desde(String linea) {
        String[] partes = linea.split(" ");
        Indicador objetivo = Indicador.desde(partes[0], partes[partes.length - 1]);
        List<Boton> botones = Arrays.stream(partes, 1, partes.length - 1).map(Boton::desde).toList();
        return new Maquina(objetivo, botones);
    }
}