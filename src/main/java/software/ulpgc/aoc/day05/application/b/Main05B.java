package software.ulpgc.aoc.day05.application.b;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day05.control.AuditBuilder;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.model.FreshnessProtocol;

public class Main05B {

    public static void main(String[] args) {

        LineLoader loader = new ResourceLineLoader("day05input");

        FreshnessProtocol dummy = (id, ranges) -> false;

        InventoryAuditor auditor = new AuditBuilder()
                .from(loader.loadLines())
                .using(dummy)
                .build();

        long totalFresh = auditor.calculateTotalCoverage();

        System.out.println("--- RESULT DAY 5 PART 2 ---");
        System.out.println("Total unique IDs considered fresh: " + totalFresh);
    }
}
