package software.ulpgc.aoc.day03;

import java.util.List;

public record StaircaseController(List<BatteryBank> banks, EnergyProtocol protocol) {
    public long activate() {
        return banks.stream()
                .mapToLong(b -> protocol.calculateEnergy(b.sequence()))
                .sum();
    }
}
