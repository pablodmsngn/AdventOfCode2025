package software.ulpgc.aoc.day02;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.function.LongPredicate;
import java.util.stream.Stream;

public class EngineBuilder {
    private InputStream file;
    private LongPredicate validator;

    public EngineBuilder from(InputStream file) {
        this.file = file;
        return this;
    }

    public EngineBuilder use(LongPredicate validator) {
        this.validator = validator;
        return this;
    }

    public Engine runner() {
        if (file == null) throw new IllegalStateException("Missing data source configuration (FROM)");
        if (validator == null) throw new IllegalStateException("Missing strategy configuration (USE)");
        Stream<IdRange> ranges = new BufferedReader(new InputStreamReader(file))
                .lines()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(IdRange::new);
        return new Engine(ranges, validator);
    }
}
