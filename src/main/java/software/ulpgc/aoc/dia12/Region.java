package software.ulpgc.aoc.dia12;

public class Region {
    private final int ancho;
    private final int alto;
    private final boolean[][] ocupado;

    public Region(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.ocupado = new boolean[alto][ancho];
    }


    public int ancho() { return ancho; }
    public int alto() { return alto; }

    public boolean estaDentro(int r, int c) {
        return r >= 0 && r < alto && c >= 0 && c < ancho;
    }

    public boolean estaOcupado(int r, int c) {
        return ocupado[r][c];
    }



    private boolean cabe(Forma forma, int r, int c) {
        for (Coordenada p : forma.puntos()) {
            int nr = r + p.r();
            int nc = c + p.c();
            if (!estaDentro(nr, nc) || estaOcupado(nr, nc)) {
                return false;
            }
        }
        return true;
    }
}