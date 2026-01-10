package software.ulpgc.aoc.dia01;



public record Dial(int posicion) {
    public Dial() {
        this(50);
    }
    public Dial {
        posicion = normalize(posicion);
    }
    public Dial rotar(int cantidad) {
        return new Dial(this.posicion + cantidad);
    }
    private static int normalize(int n) {
        return ((n % 100) + 100) % 100;
    }
}

