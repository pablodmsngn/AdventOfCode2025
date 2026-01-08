package software.ulpgc.aoc.dia02;


import java.util.function.LongPredicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public record Motor(Stream<RangoID> rangoID, LongPredicate validator) {

    public long run() {
        return rangoID
                .map(range -> range.getInvalidIds(validator)) // Aplica la estrategia
                .mapToLong(LongStream::sum)                   // Suma IDs de cada rango parcial
                .sum();                                       // Suma total
    }
}
