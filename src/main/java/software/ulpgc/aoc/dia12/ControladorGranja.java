package software.ulpgc.aoc.dia12;

import java.util.List;

public class ControladorGranja {
    private final List<DefinicionProblema> problemas;

    public ControladorGranja(List<DefinicionProblema> problemas) {
        this.problemas = problemas;
    }

    public long contarRegionesValidas() {
        // Eliminado .parallel() para evitar OutOfMemoryError
        // La optimización interna de FastSolver (usando 'long') compensa la pérdida de hilos.
        return problemas.stream()
                .filter(this::esSoluble)
                .count();
    }

    private boolean esSoluble(DefinicionProblema problema) {
        return new SolucionadorGranja().resolver(problema.ancho(), problema.alto(), problema.piezas());
    }
}