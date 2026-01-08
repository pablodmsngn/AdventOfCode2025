package software.ulpgc.aoc.dia01;


@FunctionalInterface
public interface ProtocoloSeguridad {
    /**
     * Calcula los puntos a sumar basándose en el estado anterior, el movimiento y el estado nuevo.
     */
    int calculatePoints(Dial oldDial, int movement, Dial newDial);
}
