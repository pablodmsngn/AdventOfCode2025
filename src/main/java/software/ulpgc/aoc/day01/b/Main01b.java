package software.ulpgc.aoc.day01.b;


import software.ulpgc.aoc.day01.Safe;
import software.ulpgc.aoc.day01.InputLoader;
import software.ulpgc.aoc.day01.SecurityProtocols;

public class Main01b {
    public static void main(String[] args) {
        // We inject Strategy B
        // Reuse.
        Safe safe = new Safe(SecurityProtocols.PART_B);

        InputLoader.process("day01input", safe);

        System.out.println("--- RESULT PART B ---");
        System.out.println("Password: " + safe.getZeroCount());
    }
}