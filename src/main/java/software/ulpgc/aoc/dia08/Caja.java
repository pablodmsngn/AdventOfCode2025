package software.ulpgc.aoc.dia08;

public record Caja(int x, int y, int z) {
    public double distanciaA(Caja otra) {
        return Math.sqrt(Math.pow(x - otra.x(), 2) +
                Math.pow(y - otra.y(), 2) +
                Math.pow(z - otra.z(), 2));
    }
}