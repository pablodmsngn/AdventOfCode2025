package software.ulpgc.aoc.day12.model;

public record Region(int width, int height) {
    public int area() {
        return width * height;
    }

    public boolean isSmall() {
        return area() <= 64;
    }
}
