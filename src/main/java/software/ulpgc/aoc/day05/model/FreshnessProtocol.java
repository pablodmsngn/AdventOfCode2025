package software.ulpgc.aoc.day05.model;

import java.util.List;

@FunctionalInterface
public interface FreshnessProtocol {
    boolean isFresh(long ingredientId, List<Range> ranges);
}
