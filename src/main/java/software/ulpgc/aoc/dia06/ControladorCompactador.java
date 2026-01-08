package software.ulpgc.aoc.dia06;


import java.util.stream.Stream;

public class ControladorCompactador {
    private final Stream<Operacion> operaciones;

    public ControladorCompactador(Stream<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public long ejecutar() {
        return operaciones.mapToLong(Operacion::calcular).sum();
    }
}
