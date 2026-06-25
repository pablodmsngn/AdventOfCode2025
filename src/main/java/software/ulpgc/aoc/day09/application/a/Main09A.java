package software.ulpgc.aoc.day09.application.a;

import software.ulpgc.aoc.day09.application.InputLoader;
import software.ulpgc.aoc.day09.model.AreaSolver;

public class Main09A {
    public static void main(String[] args) {
        AreaSolver cinema = InputLoader.loadMaxArea("day09input");

        System.out.println("--- DAY 9: CINEMA ---");
        System.out.println("Maximum possible area: " + cinema.solve());
    }
}
