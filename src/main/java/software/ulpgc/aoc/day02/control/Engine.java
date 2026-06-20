package software.ulpgc.aoc.day02.control;

import software.ulpgc.aoc.day02.model.IdRange;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public record Engine(Stream<IdRange> rangeId, LongPredicate validator) {

    public long run() {
        return rangeId
                .map(range -> range.getInvalidIds(validator)) // aplica la estrategia
                .mapToLong(LongStream::sum)                   // suma los IDs de cada rango
                .sum();                                       // suma total
    }
}
