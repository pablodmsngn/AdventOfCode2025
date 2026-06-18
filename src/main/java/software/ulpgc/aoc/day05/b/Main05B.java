package software.ulpgc.aoc.day05.b;

import software.ulpgc.aoc.day05.InventoryAuditor;
import software.ulpgc.aoc.day05.InputLoader;
import software.ulpgc.aoc.day05.FreshnessProtocol;

public class Main05B {

    public static void main(String[] args) {

        FreshnessProtocol dummy = (id, ranges) -> false;

        InventoryAuditor auditor = InputLoader.load("day05input", dummy);

        long totalFresh = auditor.calculateTotalCoverage();

        System.out.println("--- RESULT DAY 5 PART 2 ---");
        System.out.println("Total unique IDs considered fresh: " + totalFresh);
    }
}
