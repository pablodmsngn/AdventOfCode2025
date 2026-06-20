package software.ulpgc.aoc.day01.application.b;

import software.ulpgc.aoc.day01.application.ResourceInstructionLoader;
import software.ulpgc.aoc.day01.control.Safe;
import software.ulpgc.aoc.day01.control.SecurityProtocols;
import software.ulpgc.aoc.day01.io.InstructionLoader;
import software.ulpgc.aoc.day01.model.Instruction;

public class Main01b {
    public static void main(String[] args) {
        Safe safe = new Safe(SecurityProtocols.PART_B);
        InstructionLoader loader = new ResourceInstructionLoader("day01input");

        for (Instruction instruction : loader.loadAll()) safe.apply(instruction);

        System.out.println("--- RESULT PART B ---");
        System.out.println("Password: " + safe.getZeroCount());
    }
}
