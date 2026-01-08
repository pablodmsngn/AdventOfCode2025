package software.ulpgc.aoc.dia08;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConectorCircuitos {
    private final List<Circuito> circuitosIniciales;

    public ConectorCircuitos(List<Circuito> circuitos) {
        this.circuitosIniciales = circuitos;
    }

    // Parte 1: Obtener 'n' conexiones más cortas
    public Stream<ParCajas> obtenerConexionesMasCortas(List<Caja> cajas, long limite) {
        return flujoConexiones(cajas).limit(limite);
    }

    // Parte 2: Obtener TODAS las conexiones ordenadas
    public Stream<ParCajas> obtenerTodasLasConexiones(List<Caja> cajas) {
        return flujoConexiones(cajas);
    }

    // Lógica común de generación de pares y ordenación por distancia
    private Stream<ParCajas> flujoConexiones(List<Caja> cajas) {
        return IntStream.range(0, cajas.size())
                .parallel()
                .mapToObj(i -> IntStream.range(i + 1, cajas.size())
                        .mapToObj(cajas::get)
                        .map(caja2 -> {
                            Caja caja1 = cajas.get(i);
                            return new ParCajas(caja1, caja2, caja1.distanciaA(caja2));
                        })
                ).flatMap(s -> s)
                .sorted(Comparator.comparingDouble(ParCajas::distancia));
    }

    // Parte 1: Conectar con límite
    public long calcularFactorSeguridad(long n) {
        List<Caja> todasLasCajas = obtenerTodasLasCajas();
        ArrayList<Circuito> circuitos = new ArrayList<>(this.circuitosIniciales);

        obtenerConexionesMasCortas(todasLasCajas, n)
                .sequential()
                .forEachOrdered(par -> fusionarCircuitos(circuitos, par));

        return circuitos.stream()
                .map(c -> c.cajas().size())
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(i -> i)
                .reduce(1, (a, b) -> a * b);
    }

    // Parte 2: Conectar hasta unificar all
    public long calcularCosteUnificacion() {
        List<Caja> todasLasCajas = obtenerTodasLasCajas();
        ArrayList<Circuito> circuitos = new ArrayList<>(this.circuitosIniciales);

        // Usamos AtomicReference para capturar el par que logra la unificación final
        AtomicReference<ParCajas> parUnificador = new AtomicReference<>();

        obtenerTodasLasConexiones(todasLasCajas)
                .sequential()
                .forEachOrdered(par -> {
                    // Si ya solo queda 1 circuito, el trabajo está hecho (aunque el stream siga)
                    if (circuitos.size() == 1) return;

                    boolean fusionado = fusionarCircuitos(circuitos, par);

                    // Si hubo fusión y ahora solo queda 1 circuito, hemos encontrado el par final
                    if (fusionado && circuitos.size() == 1) {
                        parUnificador.set(par);
                    }
                });

        ParCajas ultimo = parUnificador.get();
        if (ultimo == null) throw new RuntimeException("No se pudo unificar los circuitos.");

        return (long) ultimo.caja1().x() * ultimo.caja2().x();
    }

    // Devuelve true si se realizó una fusión, false si ya estaban conectados
    private boolean fusionarCircuitos(List<Circuito> circuitos, ParCajas par) {
        Circuito c1 = buscarCircuito(par.caja1(), circuitos);

        // Si la caja 2 ya está en el mismo circuito, no hacemos nada
        if (c1.cajas().contains(par.caja2())) return false;

        Circuito c2 = buscarCircuito(par.caja2(), circuitos);

        circuitos.remove(c1);
        circuitos.remove(c2);

        Set<Caja> nuevasCajas = Stream.concat(c1.cajas().stream(), c2.cajas().stream())
                .collect(Collectors.toSet());
        circuitos.add(new Circuito(nuevasCajas));
        return true;
    }

    private Circuito buscarCircuito(Caja caja, List<Circuito> circuitos) {
        for (Circuito circuito : circuitos) {
            if (circuito.cajas().contains(caja)) return circuito;
        }
        throw new RuntimeException("Caja huérfana no encontrada: " + caja);
    }

    private List<Caja> obtenerTodasLasCajas() {
        return circuitosIniciales.stream()
                .flatMap(c -> c.cajas().stream())
                .toList();
    }
}