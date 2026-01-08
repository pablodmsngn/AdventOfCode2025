package software.ulpgc.aoc.dia07;


import java.util.List;

public class ControladorLaboratorio {
    private final List<List<Celda>> rejilla;

    public ControladorLaboratorio(List<List<Celda>> rejilla) {
        this.rejilla = rejilla;
    }

    // Parte 1: Contar eventos de división

    public int contarDivisiones() {
        // Iniciamos en capa 1, asumiendo que capa 0 es la entrada S
        return new SimuladorTaquiones(rejilla, 0, 1)
                .resolver()
                .divisionesAcumuladas();
    }

    // Parte 2: Contar líneas temporales totales (suma de intensidades al final)
    public long contarLineasTemporales() {
        var simulador = new SimuladorTaquiones(rejilla, 0, 1).resolver();

        // Obtenemos la última capa de la rejilla resultante
        var ultimaCapa = simulador.rejilla().get(simulador.rejilla().size() - 1);

        // Sumamos las intensidades (caminos acumulados) de los haces que llegaron al fondo
        return ultimaCapa.stream()
                .filter(Celda::esHaz)
                .mapToLong(Celda::intensidad)
                .sum();
    }
}