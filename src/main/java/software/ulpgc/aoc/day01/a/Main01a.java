package software.ulpgc.aoc.day01.a;


import software.ulpgc.aoc.day01.Safe;
import software.ulpgc.aoc.day01.InputLoader;
import software.ulpgc.aoc.day01.SecurityProtocols;

public class Main01a {
    public static void main(String[] args) {
        // We inject Strategy A
        Safe safe = new Safe(SecurityProtocols.PART_A);
        //SecurityProtocols securityProtocols = new SecurityProtocols();

        InputLoader.process("day01input", safe);

        System.out.println("--- RESULT PART A ---");
        System.out.println("Password: " + safe.getZeroCount());
    }
}