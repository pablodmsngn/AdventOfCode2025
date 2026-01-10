package software.ulpgc.aoc.dia02;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public record RangoID(long comienzoID, long finalID) {
    ///la funcion es actuar como un traductor: recibe el formato "sucio" del fichero de texto
    /// (un String como "10-20") y lo convierte en el formato "limpio" que necesita el objeto (dos números long).
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
