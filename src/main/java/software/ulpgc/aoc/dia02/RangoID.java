package software.ulpgc.aoc.dia02;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public record RangoID(long comienzoID, long finalID) {
    public RangoID(String rangoID) {
        this(
                Long.parseLong(rangoID.substring(0, rangoID.indexOf('-'))),
                Long.parseLong(rangoID.substring(rangoID.indexOf('-') + 1))
        );
    }

    public LongStream getIds() {
        return LongStream.rangeClosed(comienzoID, finalID);
    }

    public LongStream getInvalidIds(LongPredicate validator) {
        return getIds().filter(validator.negate());
    }

}
