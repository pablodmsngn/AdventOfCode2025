package software.ulpgc.aoc.dia02;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.function.LongPredicate;
import java.util.stream.Stream;

public class ConstructorMotor {
    private InputStream file;
    private LongPredicate validator;

    public ConstructorMotor from(InputStream file) {
        this.file = file;
        return this;
    }

    public ConstructorMotor use(LongPredicate validator) {
        this.validator = validator;
        return this;
    }

    public Motor runner() {
        if (file == null) throw new IllegalStateException("Falta configurar la fuente de datos (FROM)");
        if (validator == null) throw new IllegalStateException("Falta configurar la estrategia (USE)");
        Stream<RangoID> rangos = new BufferedReader(new InputStreamReader(file))
                .lines()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(RangoID::new);
        return new Motor(rangos, validator);
    }
}
