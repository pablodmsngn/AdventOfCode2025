package software.ulpgc.aoc.dia01;

//clase de utilidad
public class ProtocolosSeguridad {

    public static final ProtocoloSeguridad PART_A = (oldDial, movement, newDial) ->
            newDial.posicion() == 0 ? 1 : 0;

    public static final ProtocoloSeguridad PART_B = (oldDial, movement, newDial) -> {
        long start = oldDial.posicion();
        long end = start + movement;
        long min = (movement > 0) ? start + 1 : end;
        long max = (movement > 0) ? end : start - 1;
        return (int) (Math.floorDiv(max, 100) - Math.floorDiv(min - 1, 100));
    };
}