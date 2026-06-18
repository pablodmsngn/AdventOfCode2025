package software.ulpgc.aoc.day01;

// utility class
public class SecurityProtocols {

    public static final SecurityProtocol PART_A = (oldDial, movement, newDial) -> // implements the interface: previous dial, how much it moved and the current one
            newDial.position() == 0 ? 1 : 0; // If the dial ended at position 0, returns 1 point. Otherwise, returns 0

    public static final SecurityProtocol PART_B = (oldDial, movement, newDial) -> {
        long start = oldDial.position();
        long end = start + movement;
        long min = (movement > 0) ? start + 1 : end;
        long max = (movement > 0) ? end : start - 1;
        return (int) (Math.floorDiv(max, 100) - Math.floorDiv(min - 1, 100));
    };
}