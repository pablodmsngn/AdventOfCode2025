package software.ulpgc.aoc.day07.a;


import software.ulpgc.aoc.day07.InputLoader;
import software.ulpgc.aoc.day07.LabController;

public class Main07A {
    public static void main(String[] args) {

        LabController laboratory = InputLoader.load("day07input");

        System.out.println("--- DAY 7: LABORATORIES ---");
        int result = laboratory.countDivisions();
        System.out.println("Total beam divisions: " + result);
    }
}
