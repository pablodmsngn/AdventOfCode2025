package software.ulpgc.aoc.day09;


import java.util.List;

public class CinemaController {
    private final List<Coordinate> redTiles;

    public CinemaController(List<Coordinate> redTiles) {
        this.redTiles = redTiles;
    }

    public long getMaxArea() {
        return new RectangleFinder(redTiles)
                .findLargest()
                .area();
    }
    // part2
    public long getMaxAllowedArea() {
        return new RectangleFinder(redTiles).findLargestAllowed().area();
    }
}