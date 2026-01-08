package software.ulpgc.aoc.dia12;

public record Coordenada(int r, int c) {
    public Coordenada rotar() {
        // Rotación 90 grados: (r, c) -> (c, -r)
        return new Coordenada(c, -r);
    }

    public Coordenada voltear() {
        // Espejo horizontal: (r, c) -> (r, -c)
        return new Coordenada(r, -c);
    }

}