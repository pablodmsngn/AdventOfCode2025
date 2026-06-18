package software.ulpgc.aoc.day05;

import java.util.List;

@FunctionalInterface
public interface FreshnessProtocol {
    boolean isFresh(long ingredientId, List<Range> ranges);
}
