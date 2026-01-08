package software.ulpgc.aoc.dia07;



public record Celda(TipoCelda tipo, long intensidad) {

    public static Celda vacia() {
        return new Celda(TipoCelda.VACIO, 0);
    }

    public static Celda divisor() {
        return new Celda(TipoCelda.DIVISOR, 0);
    }

    public static Celda haz(long intensidad) {
        return new Celda(TipoCelda.HAZ, intensidad);
    }


    public static Celda haz() {
        return new Celda(TipoCelda.HAZ, 1);
    }

    public static Celda desdeCaracter(char c) {
        if (c == '^') return divisor();
        if (c == 'S') return haz();
        return vacia();
    }


    public boolean esDivisor() { return tipo == TipoCelda.DIVISOR; }
    public boolean esHaz() { return tipo == TipoCelda.HAZ; }
}