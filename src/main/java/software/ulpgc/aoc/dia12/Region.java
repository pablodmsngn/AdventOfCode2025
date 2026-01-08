package software.ulpgc.aoc.dia12;

public record Region(int ancho, int alto) {
    public int area() {
        return ancho * alto;
    }

    public boolean esPequena() {
        return area() <= 64;
    }
}