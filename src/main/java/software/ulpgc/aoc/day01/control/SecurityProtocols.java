package software.ulpgc.aoc.day01.control;

import software.ulpgc.aoc.day01.model.SecurityProtocol;


public class SecurityProtocols {

    //a:1 punto si el giro termina exactamente en 0
    public static final SecurityProtocol PART_A = (oldDial, movement, newDial) ->
            newDial.position() == 0 ? 1 : 0;

    //b:cuenta cuantas veces el giro cruza el 0
    public static final SecurityProtocol PART_B = (oldDial, movement, newDial) -> {
        long start = oldDial.position();
        long end = start + movement;
        long min = (movement > 0) ? start + 1 : end;
        long max = (movement > 0) ? end : start - 1;
        return (int) (Math.floorDiv(max, 100) - Math.floorDiv(min - 1, 100));
    };
}
