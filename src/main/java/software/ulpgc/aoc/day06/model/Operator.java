package software.ulpgc.aoc.day06.model;

import java.util.function.BinaryOperator;

public enum Operator implements BinaryOperator<Long> {
    ADD, MULTIPLY;

    public static boolean isOperator(char c) {
        return c == '+' || c == '*';
    }

    public static Operator from(String str) {
        return str.strip().equals("+") ? ADD : MULTIPLY;
    }

    public long identity() {
        return this.equals(ADD) ? 0 : 1;
    }

    @Override
    public Long apply(Long a, Long b) {
        return this.equals(ADD) ? a + b : a * b;
    }
}
