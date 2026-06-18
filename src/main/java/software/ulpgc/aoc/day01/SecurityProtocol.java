package software.ulpgc.aoc.day01;


@FunctionalInterface
public interface SecurityProtocol {
    int calculatePoints(Dial oldDial, int movement, Dial newDial);
}
