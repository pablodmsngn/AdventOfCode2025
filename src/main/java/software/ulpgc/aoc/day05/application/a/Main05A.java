package software.ulpgc.aoc.day05.application.a;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day05.control.AuditBuilder;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.model.FreshnessProtocol;

public class Main05A {

    public static void main(String[] args) {

        LineLoader loader = new ResourceLineLoader("day05input");

        FreshnessProtocol standardPolicy = (id, ranges) ->
                ranges.stream().anyMatch(range -> range.contains(id));

        InventoryAuditor auditor = new AuditBuilder()
                .from(loader.loadLines())
                .using(standardPolicy)
                .build();

        long result = auditor.audit();

        System.out.println("--- RESULT DAY 5 ---");
        System.out.println("Fresh ingredients found: " + result);
    }
}
