package software.ulpgc.aoc.day12.control;

import software.ulpgc.aoc.day12.model.Coordinate;
import software.ulpgc.aoc.day12.model.Region;
import software.ulpgc.aoc.day12.model.Shape;

import java.util.*;

public class FarmSolver {

    public boolean solve(Region region, List<Shape> pieces) {
        if (pieces.stream().mapToInt(Shape::area).sum() > region.area()) {
            return false;
        }

        List<Shape> sortedPieces = new ArrayList<>(pieces);
        sortedPieces.sort(Comparator.comparingInt(Shape::area).reversed()
                .thenComparingInt(Shape::id));

        if (region.isSmall()) {
            return solveWithPrimitives(region, sortedPieces);
        } else {
            return solveWithBitSet(region, sortedPieces);
        }
    }

    private boolean solveWithPrimitives(Region region, List<Shape> pieces) {
        Map<Integer, long[]> cache = precomputeLongMasks(region, pieces);
        if (cache.size() != pieces.stream().map(Shape::id).distinct().count()) return false;

        return backtrackLong(0, 0L, pieces, cache, -1);
    }

    private boolean backtrackLong(int idx, long board, List<Shape> pieces, Map<Integer, long[]> cache, int lastOptionIndex) {
        if (idx == pieces.size()) return true;

        Shape piece = pieces.get(idx);
        long[] options = cache.get(piece.id());

        boolean isEqual = (idx > 0) && (piece.id() == pieces.get(idx - 1).id());
        int start = isEqual ? lastOptionIndex + 1 : 0;

        for (int i = start; i < options.length; i++) {
            if ((board & options[i]) == 0) {
                if (backtrackLong(idx + 1, board | options[i], pieces, cache, i)) return true;
            }
        }
        return false;
    }

    private boolean solveWithBitSet(Region region, List<Shape> pieces) {
        Map<Integer, List<BitSet>> cache = precomputeBitSetMasks(region, pieces);
        if (cache.size() != pieces.stream().map(Shape::id).distinct().count()) return false;

        return backtrackBitSet(0, new BitSet(region.area()), pieces, cache, -1);
    }

    private boolean backtrackBitSet(int idx, BitSet board, List<Shape> pieces, Map<Integer, List<BitSet>> cache, int lastOptionIndex) {
        if (idx == pieces.size()) return true;

        Shape piece = pieces.get(idx);
        List<BitSet> options = cache.get(piece.id());

        boolean isEqual = (idx > 0) && (piece.id() == pieces.get(idx - 1).id());
        int start = isEqual ? lastOptionIndex + 1 : 0;

        for (int i = start; i < options.size(); i++) {
            BitSet option = options.get(i);
            if (!board.intersects(option)) {
                board.or(option);
                if (backtrackBitSet(idx + 1, board, pieces, cache, i)) return true;
                board.xor(option);
            }
        }
        return false;
    }

    private Map<Integer, long[]> precomputeLongMasks(Region region, List<Shape> pieces) {
        Map<Integer, long[]> cache = new HashMap<>();
        for (Shape p : pieces) {
            if (!cache.containsKey(p.id())) {
                List<Long> opts = new ArrayList<>();
                generateOptions(p, region, (r, c, pts) -> {
                    long mask = 0L;
                    for (Coordinate pt : pts) mask |= (1L << (pt.r() * region.width() + pt.c()));
                    opts.add(mask);
                });
                if (!opts.isEmpty()) cache.put(p.id(), opts.stream().mapToLong(l -> l).toArray());
            }
        }
        return cache;
    }

    private Map<Integer, List<BitSet>> precomputeBitSetMasks(Region region, List<Shape> pieces) {
        Map<Integer, List<BitSet>> cache = new HashMap<>();
        for (Shape p : pieces) {
            if (!cache.containsKey(p.id())) {
                List<BitSet> opts = new ArrayList<>();
                generateOptions(p, region, (r, c, pts) -> {
                    BitSet bs = new BitSet(region.area());
                    for (Coordinate pt : pts) bs.set(pt.r() * region.width() + pt.c());
                    opts.add(bs);
                });
                if (!opts.isEmpty()) cache.put(p.id(), opts);
            }
        }
        return cache;
    }


    interface MaskConsumer {
        void accept(int r, int c, List<Coordinate> translatedPoints);
    }

    private void generateOptions(Shape piece, Region region, MaskConsumer consumer) {
        for (Shape var : piece.generateVariations()) {
            for (int r = 0; r < region.height(); r++) {
                for (int c = 0; c < region.width(); c++) {
                    List<Coordinate> translated = new ArrayList<>();
                    boolean valid = true;
                    for (Coordinate p : var.points()) {
                        int nr = r + p.r();
                        int nc = c + p.c();
                        if (nr < 0 || nr >= region.height() || nc < 0 || nc >= region.width()) {
                            valid = false; break;
                        }
                        translated.add(new Coordinate(nr, nc));
                    }
                    if (valid) consumer.accept(r, c, translated);
                }
            }
        }
    }
}
