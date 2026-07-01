package software.ulpgc.aoc.day05.application.b;

import software.ulpgc.aoc.day05.application.InputLoader;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.model.FreshnessProtocol;

public class Main05B {

    public static void main(String[] args) {

        FreshnessProtocol dummy = (id, ranges) -> false;

        InventoryAuditor auditor = InputLoader.load("day05input", dummy);

        long totalFresh = auditor.calculateTotalCoverage();

        System.out.println("--- RESULT DAY 5 PART 2 ---");
        System.out.println("Total unique IDs considered fresh: " + totalFresh);
    }
}
