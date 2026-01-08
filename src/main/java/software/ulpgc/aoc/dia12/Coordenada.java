package software.ulpgc.aoc.dia12;

public record Coordenada(int r, int c) {
    public Coordenada rotar() {
        return new Coordenada(c, -r);
    }

    public Coordenada voltear() {
        return new Coordenada(r, -c);
    }

}