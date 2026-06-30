package software.ulpgc.aoc.day01.application.a;

import software.ulpgc.aoc.day01.application.InputLoader;
import software.ulpgc.aoc.day01.control.Safe;
import software.ulpgc.aoc.day01.control.SecurityProtocols;
import software.ulpgc.aoc.day01.model.Instruction;

public class Main01a {
    public static void main(String[] args) {
        Safe safe = new Safe(SecurityProtocols.PART_A);

        for (Instruction instruction : InputLoader.load("day01input")) safe.apply(instruction);

        System.out.println("--- RESULT PART A ---");
        System.out.println("Password: " + safe.getZeroCount());
    }
}
