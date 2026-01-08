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

        // Usamos la misma lista de botones de la instancia
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

    //BFS OPTIMIZADO CON MaSCARA DE BITS
    private List<Set<Boton>> buscarSolucionesLuces(Indicador target) {
        List<Set<Boton>> soluciones = new ArrayList<>();
        // Usamos Long para representar el conjunto de botones (bit = 1 -> pulsado)
        // Esto es mucho más rápido que Set<Set<Boton>>
        Set<Long> visitados = new HashSet<>();
        Queue<Long> cola = new ArrayDeque<>();

        // Estado inicial: máscara 0 (ningún botón)
        cola.add(0L);
        visitados.add(0L);

        // Pre-calculamos el estado inicial de luces para evitar creaciones constantes
        Indicador base = target.crearEstadoInicial();
        List<Estado> targetEstados = target.estados();

        while (!cola.isEmpty()) {
            long mask = cola.poll();

            // Calculamos el estado de luces para esta máscara
            // Solo nos interesan las luces (estados), no los voltajes aquí
            if (calcularSoloEstados(base, mask).equals(targetEstados)) {
                soluciones.add(convertirMascaraASet(mask));
            }

            // Generamos hijos
            for (int i = 0; i < botones.size(); i++) {
                // Si el botón ya está en la máscara, no lo añadimos (evita ciclos simples)
                // y evita redundancia porque el orden no importa en la suma
                if ((mask & (1L << i)) != 0) continue;

                long nuevaMask = mask | (1L << i);

                if (visitados.add(nuevaMask)) {
                    cola.add(nuevaMask);
                }
            }
        }
        return soluciones;
    }

    // Metodo auxiliar optimizado para verificar solo luces
    private List<Estado> calcularSoloEstados(Indicador base, long mask) {
        // Hacemos una copia mutable rápida o calculamos al vuelo
        // Dado que Estado es inmutable, mapeamos los índices.
        return IntStream.range(0, base.estados().size())
                .mapToObj(i -> {
                    boolean encendidoInicial = (base.estados().get(i) == Estado.ENCENDIDO);
                    // Verificamos cuántos botones activos afectan a este índice 'i'
                    long toggleCount = 0;
                    for (int b = 0; b < botones.size(); b++) {
                        if ((mask & (1L << b)) != 0) { // Si el botón 'b' está pulsado
                            if (botones.get(b).indicesAfectados().contains(i)) {
                                toggleCount++;
                            }
                        }
                    }
                    // Si es impar, cambiamos estado. Si es par, se queda igual.
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
    // ------------------------------------------

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
        // Este metodo se usa en la recursión (menos frecuente), puede quedarse con Streams
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