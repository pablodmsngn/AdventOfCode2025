package software.ulpgc.aoc.day12.application.a;

import software.ulpgc.aoc.day12.application.InputLoader;
import software.ulpgc.aoc.day12.control.FarmController;

public class Main12A {
    public static void main(String[] args) {
        FarmController farm = InputLoader.load("day12input");

        System.out.println("--- DAY 12: TREE FARM ---");
        long result = farm.countValidRegions();

        System.out.println("Regions where all gifts fit: " + result);
    }
}
