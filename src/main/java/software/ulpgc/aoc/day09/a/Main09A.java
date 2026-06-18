package software.ulpgc.aoc.day09.a;


import software.ulpgc.aoc.day09.InputLoader;
import software.ulpgc.aoc.day09.CinemaController;

public class Main09A {
    public static void main(String[] args) {

        CinemaController cinema = InputLoader.load("day09input");

        System.out.println("--- DAY 9: CINEMA ---");
        System.out.println("Maximum possible area: " + cinema.getMaxArea());
    }
}
