package software.ulpgc.aoc.common.io;

import java.util.List;

/**
 * Shared input port: reads the raw lines of a resource.
 * Used by every day; parsing into the domain happens afterwards in each day's InputLoader/builder.
 */
public interface LineLoader {
    List<String> loadLines();
}
