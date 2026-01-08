package software.ulpgc.aoc.dia06;


import java.util.List;

public record Operacion(List<Long> operandos, Operador operador) {
    public long calcular() {
        return operandos.stream().reduce(operador.identidad(), operador);
    }
}
