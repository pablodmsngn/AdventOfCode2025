package software.ulpgc.aoc.day07.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day07.control.GridBuilder;
import software.ulpgc.aoc.day07.control.LabController;
import software.ulpgc.aoc.day07.model.LabProtocol;

public class InputLoader {
    public static LabController load(String file, LabProtocol protocol) {
        LineLoader loader = new ResourceLineLoader(file);
        return new GridBuilder()
                .from(loader.loadLines())
                .using(protocol)
                .build();
    }
}
