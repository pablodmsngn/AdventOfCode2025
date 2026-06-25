package software.ulpgc.aoc.day09.application.b;

import software.ulpgc.aoc.day09.application.InputLoader;
import software.ulpgc.aoc.day09.model.AreaSolver;

public class Main09B {
    public static void main(String[] args) {
        AreaSolver cinema = InputLoader.loadAllowedArea("day09input");

        System.out.println("--- DAY 9 PART 2: CINEMA ---");
        System.out.println("Maximum allowed area (Red/Green): " + cinema.solve());
    }
}
