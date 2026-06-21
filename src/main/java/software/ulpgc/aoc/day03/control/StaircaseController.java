package software.ulpgc.aoc.day03.control;

import software.ulpgc.aoc.day03.model.BatteryBank;
import software.ulpgc.aoc.day03.model.EnergyProtocol;

import java.util.List;

public record StaircaseController(List<BatteryBank> banks, EnergyProtocol protocol) {
    public long activate() {
        return banks.stream()
                .mapToLong(b -> protocol.calculateEnergy(b.sequence()))
                .sum();
    }
}
