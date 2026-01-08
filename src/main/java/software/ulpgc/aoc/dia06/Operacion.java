package software.ulpgc.aoc.dia06;


import java.util.List;

public record Operacion(List<Long> operandos, Operador operador) {
    // Lógica funcional pura: reduce la lista a un único valor
    public long calcular() {
        return operandos.stream().reduce(operador.identidad(), operador);
    }
}
