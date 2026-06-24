package software.ulpgc.aoc.day07.application;

import software.ulpgc.aoc.day07.control.GridBuilder;
import software.ulpgc.aoc.day07.control.LabController;
import software.ulpgc.aoc.day07.io.GridLoader;
import software.ulpgc.aoc.day07.model.LabProtocol;

public class InputLoader {
    public static LabController load(String file, LabProtocol protocol) {
        GridLoader loader = new ResourceGridLoader(file);
        return new GridBuilder()
                .from(loader.loadLines())
                .using(protocol)
                .build();
    }
}
