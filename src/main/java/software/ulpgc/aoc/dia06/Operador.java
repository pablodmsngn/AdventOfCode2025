package software.ulpgc.aoc.dia06;


import java.util.function.BinaryOperator;

public enum Operador implements BinaryOperator<Long> {
    SUMAR, MULTIPLICAR;

    public static boolean esOperador(char c) {
        return c == '+' || c == '*';
    }

    public static Operador desde(String str) {
        return str.strip().equals("+") ? SUMAR : MULTIPLICAR;
    }

    public long identidad() {
        return this.equals(SUMAR) ? 0 : 1;
    }

    @Override
    public Long apply(Long a, Long b) {
        return this.equals(SUMAR) ? a + b : a * b;
    }


}