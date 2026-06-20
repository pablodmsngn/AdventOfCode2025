package software.ulpgc.aoc.day02.model;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;


public record IdRange(long startId, long finalId) {


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
