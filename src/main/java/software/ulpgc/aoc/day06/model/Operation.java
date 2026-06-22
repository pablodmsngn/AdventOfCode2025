package software.ulpgc.aoc.day06.model;

import java.util.List;

public record Operation(List<Long> operands, Operator operator) {
    public long calculate() {
        return operands.stream().reduce(operator.identity(), operator);
    }
}
