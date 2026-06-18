package software.ulpgc.aoc.day07.b;

import software.ulpgc.aoc.day07.InputLoader;
import software.ulpgc.aoc.day07.LabController;

public class Main07B {
    public static void main(String[] args) {
        LabController laboratory = InputLoader.load("day07input");

        System.out.println("--- DAY 7 PART 2: TACHYONIC MULTIVERSE ---");

        long totalLines = laboratory.countTemporalLines();
        System.out.println("Total active timelines: " + totalLines);
    }
}