package software.ulpgc.aoc.dia02;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public record RangoID(long comienzoID, long finalID) {
    // Constructor secundario para parsear "11-22"
    public RangoID(String rangoID) {
        this(
                Long.parseLong(rangoID.substring(0, rangoID.indexOf('-'))),
                Long.parseLong(rangoID.substring(rangoID.indexOf('-') + 1))
        );
    }

    public LongStream getIds() {
        return LongStream.rangeClosed(comienzoID, finalID);
    }
    // Recibe la estrategia (validator) y filtra
    public LongStream getInvalidIds(LongPredicate validator) {
        // filter espera true para mantener, así que negamos si el validador devuelve true para "válidos"
        return getIds().filter(validator.negate());
    }

}
