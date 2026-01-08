package software.ulpgc.aoc.dia12;
import java.util.List;

public class ControladorGranja {
    private final List<DefinicionProblema> problemas;

    public ControladorGranja(List<DefinicionProblema> problemas) {
        this.problemas = problemas;
    }

    public long contarRegionesValidas() {
        return problemas.stream()
                .filter(this::esSoluble)
                .count();
    }

    private boolean esSoluble(DefinicionProblema p) {
        return new SolucionadorGranja().resolver(p.region(), p.piezas());
    }
}