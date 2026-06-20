package software.ulpgc.aoc.day02.control;

import software.ulpgc.aoc.day02.model.IdRange;

import java.util.function.LongPredicate;
import java.util.stream.Stream;


public class EngineBuilder {
    private Stream<IdRange> ranges;
    private LongPredicate validator;

    public EngineBuilder from(Stream<IdRange> ranges) {
        this.ranges = ranges;
        return this;
    }

    public EngineBuilder use(LongPredicate validator) {
        this.validator = validator;
        return this;
    }

    public Engine runner() {
        if (ranges == null) throw new IllegalStateException("Missing data source configuration (FROM)");
        if (validator == null) throw new IllegalStateException("Missing strategy configuration (USE)");
        return new Engine(ranges, validator);
    }
}
