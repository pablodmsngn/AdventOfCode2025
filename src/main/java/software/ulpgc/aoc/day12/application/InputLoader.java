package software.ulpgc.aoc.day12.application;

import software.ulpgc.aoc.day12.control.FarmController;
import software.ulpgc.aoc.day12.io.ProblemLoader;
import software.ulpgc.aoc.day12.model.Coordinate;
import software.ulpgc.aoc.day12.model.ProblemDefinition;
import software.ulpgc.aoc.day12.model.Region;
import software.ulpgc.aoc.day12.model.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputLoader {

    public static FarmController load(String file) {
        ProblemLoader loader = new ResourceProblemLoader(file);
        return fromLines(loader.loadLines());
    }

    public static FarmController fromLines(List<String> lines) {
        Map<Integer, Shape> catalog = new HashMap<>();
        List<ProblemDefinition> problems = new ArrayList<>();

        int currentId = -1;
        List<String> shapeBuffer = new ArrayList<>();

        for (String line : lines) {
            if (line.isBlank()) continue;

            if (line.contains(":")) {
                if (currentId != -1 && !shapeBuffer.isEmpty()) {
                    catalog.put(currentId, createShape(currentId, shapeBuffer));
                    shapeBuffer.clear();
                }

                if (line.contains("x")) {
                    currentId = -1;
                    problems.add(parseProblem(line, catalog));
                } else {
                    currentId = Integer.parseInt(line.replace(":", "").trim());
                }
            } else if (currentId != -1) {
                shapeBuffer.add(line);
            }
        }

        if (currentId != -1 && !shapeBuffer.isEmpty()) {
            catalog.put(currentId, createShape(currentId, shapeBuffer));
        }

        return new FarmController(problems);
    }

    private static Shape createShape(int id, List<String> rows) {
        List<Coordinate> points = new ArrayList<>();
        for (int r = 0; r < rows.size(); r++) {
            String row = rows.get(r);
            for (int c = 0; c < row.length(); c++) {
                if (row.charAt(c) == '#') points.add(new Coordinate(r, c));
            }
        }
        return new Shape(id, points);
    }

    private static ProblemDefinition parseProblem(String line, Map<Integer, Shape> catalog) {
        String[] parts = line.split(":");
        String[] dims = parts[0].trim().split("x");
        int w = Integer.parseInt(dims[0]);
        int h = Integer.parseInt(dims[1]);

        List<Shape> pieces = new ArrayList<>();
        String[] accounts = parts[1].trim().split("\\s+");

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].isBlank()) continue;
            int quantity = Integer.parseInt(accounts[i]);
            if (quantity > 0) {
                Shape prototype = catalog.get(i);
                if (prototype == null) throw new RuntimeException("Error: Shape ID=" + i + " has not been defined before use.");
                for (int k = 0; k < quantity; k++) pieces.add(prototype);
            }
        }
        return new ProblemDefinition(new Region(w, h), pieces);
    }
}
