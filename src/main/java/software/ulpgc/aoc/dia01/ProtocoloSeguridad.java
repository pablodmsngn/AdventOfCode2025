package software.ulpgc.aoc.dia01;


@FunctionalInterface
public interface ProtocoloSeguridad {
    int calculatePoints(Dial oldDial, int movement, Dial newDial);
}
