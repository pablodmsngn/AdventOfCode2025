package software.ulpgc.aoc.day05.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day05.control.AuditBuilder;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.model.FreshnessProtocol;

public class InputLoader {
    public static InventoryAuditor load(String file, FreshnessProtocol protocol) {
        LineLoader loader = new ResourceLineLoader(file);
        return new AuditBuilder()
                .from(loader.loadLines())
                .using(protocol)
                .build();
    }
}
