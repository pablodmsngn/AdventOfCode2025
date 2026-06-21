package software.ulpgc.aoc.day03.io;

import software.ulpgc.aoc.day03.model.BatteryBank;

import java.util.List;

public interface SequenceLoader {
    List<BatteryBank> loadAll();
}
