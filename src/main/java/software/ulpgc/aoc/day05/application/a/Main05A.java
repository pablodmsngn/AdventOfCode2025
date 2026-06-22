package software.ulpgc.aoc.day05.application.a;

import software.ulpgc.aoc.day05.application.ResourceAuditLoader;
import software.ulpgc.aoc.day05.control.AuditBuilder;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.io.AuditLoader;
import software.ulpgc.aoc.day05.model.FreshnessProtocol;

public class Main05A {

    public static void main(String[] args) {

        AuditLoader loader = new ResourceAuditLoader("day05input");

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
