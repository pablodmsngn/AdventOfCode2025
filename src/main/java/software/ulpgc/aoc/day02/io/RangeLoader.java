package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;

import java.util.stream.Stream;


public interface RangeLoader {
    Stream<IdRange> loadAll();
}
