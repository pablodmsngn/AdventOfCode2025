package software.ulpgc.aoc.day02;


import java.util.function.LongPredicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public record Engine(Stream<IdRange> rangeId, LongPredicate validator) {

    public long run() {
        return rangeId
                .map(range -> range.getInvalidIds(validator)) // Apply the strategy
                .mapToLong(LongStream::sum)                   // Sum IDs of each partial range
                .sum();                                       // Total sum
    }
}
