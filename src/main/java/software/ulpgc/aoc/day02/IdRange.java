package software.ulpgc.aoc.day02;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public record IdRange(long startId, long finalId) {
    /// the function acts as a translator: it receives the "dirty" format from the text file
    /// (a String like "10-20") and converts it into the "clean" format the object needs (two long numbers).
    public IdRange(String rangeId) {
        this(
                Long.parseLong(rangeId.substring(0, rangeId.indexOf('-'))),
                Long.parseLong(rangeId.substring(rangeId.indexOf('-') + 1))
        );
    }

    public LongStream getIds() {
        return LongStream.rangeClosed(startId, finalId);
    }

    public LongStream getInvalidIds(LongPredicate validator) {
        return getIds().filter(validator.negate());
    }

}
