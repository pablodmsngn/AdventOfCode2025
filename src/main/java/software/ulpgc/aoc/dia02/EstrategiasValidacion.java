package software.ulpgc.aoc.dia02;

import java.util.function.LongPredicate;

public class EstrategiasValidacion {

    public static final LongPredicate PATRON_A = id -> {
        return !Long.toString(id).matches("^(\\d+)\\1$");
    };

    public static final LongPredicate PATRON_B = id -> {

        return !Long.toString(id).matches("^(\\d+)\\1+$");
    };
}
