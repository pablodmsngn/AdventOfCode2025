package software.ulpgc.aoc.day12;

public record Region(int width, int height) {
    public int area() {
        return width * height;
    }

    public boolean isSmall() {
        return area() <= 64;
    }
}