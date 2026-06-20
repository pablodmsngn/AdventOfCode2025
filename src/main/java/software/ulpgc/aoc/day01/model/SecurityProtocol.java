package software.ulpgc.aoc.day01.model;


public interface SecurityProtocol {
    int calculatePoints(Dial oldDial, int movement, Dial newDial);
}
