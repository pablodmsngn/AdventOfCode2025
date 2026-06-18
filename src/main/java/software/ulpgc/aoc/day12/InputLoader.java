package software.ulpgc.aoc.day12;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class InputLoader {

    public static FarmController load(String file) {
        InputStream is = InputLoader.class.getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);
        return load(is);
    }

    public static FarmController load(InputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            return process(reader.lines().toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static FarmController process(List<String> lines) {
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