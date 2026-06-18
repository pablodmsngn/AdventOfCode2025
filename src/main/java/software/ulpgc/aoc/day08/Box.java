package software.ulpgc.aoc.day08;

public record Box(int x, int y, int z) {
    public double distanceTo(Box another) {
        return Math.sqrt(Math.pow(x - another.x(), 2) +
                Math.pow(y - another.y(), 2) +
                Math.pow(z - another.z(), 2));
    }
}