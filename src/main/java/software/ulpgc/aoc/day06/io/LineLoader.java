package software.ulpgc.aoc.day06.io;

import java.util.stream.Stream;

public interface LineLoader {
    Stream<String> load();
}
