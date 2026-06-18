package software.ulpgc.aoc.day09.b;


import software.ulpgc.aoc.day09.InputLoader;
import software.ulpgc.aoc.day09.CinemaController;

public class Main09B {
    public static void main(String[] args) {
        CinemaController cinema = InputLoader.load("day09input");

        System.out.println("--- DAY 9 PART 2: CINEMA ---");
        System.out.println("Maximum allowed area (Red/Green): " + cinema.getMaxAllowedArea());
    }
}
