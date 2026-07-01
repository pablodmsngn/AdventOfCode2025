package software.ulpgc.aoc.day05.application.a;

import software.ulpgc.aoc.day05.application.InputLoader;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.model.FreshnessProtocol;

public class Main05A {

    public static void main(String[] args) {

        FreshnessProtocol standardPolicy = (id, ranges) ->
                ranges.stream().anyMatch(range -> range.contains(id));

        InventoryAuditor auditor = InputLoader.load("day05input", standardPolicy);

        long result = auditor.audit();

        System.out.println("--- RESULT DAY 5 ---");
        System.out.println("Fresh ingredients found: " + result);
    }
}
