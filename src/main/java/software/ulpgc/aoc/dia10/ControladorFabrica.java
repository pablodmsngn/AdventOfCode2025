package software.ulpgc.aoc.dia10;

import java.util.HashMap;
import java.util.List;

public class ControladorFabrica {
    private final List<Maquina> maquinas;

    public ControladorFabrica(List<Maquina> maquinas) {
        this.maquinas = maquinas;
    }

    public long ejecutarParte1() {
        return maquinas.stream()
                .mapToInt(Maquina::resolverPulsacionesMinimas)
                .sum();
    }

    public long ejecutarParte2() {
        return maquinas.stream()
                .mapToInt(m->m.resolverRequisitosVoltaje(new HashMap<>()))
                .sum();
    }
}