package software.ulpgc.aoc.dia07;


import java.util.List;

public class ControladorLaboratorio {
    private final List<List<Celda>> rejilla;

    public ControladorLaboratorio(List<List<Celda>> rejilla) {
        this.rejilla = rejilla;
    }



    public int contarDivisiones() {
        return new SimuladorTaquiones(rejilla, 0, 1)
                .resolver()
                .divisionesAcumuladas();
    }

    public long contarLineasTemporales() {
        var simulador = new SimuladorTaquiones(rejilla, 0, 1).resolver();
        var ultimaCapa = simulador.rejilla().get(simulador.rejilla().size() - 1);
        return ultimaCapa.stream()
                .filter(Celda::esHaz)
                .mapToLong(Celda::intensidad)
                .sum();
    }
}