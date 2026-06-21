package software.ulpgc.aoc.day03.control;

import software.ulpgc.aoc.day03.model.BatteryBank;
import software.ulpgc.aoc.day03.model.EnergyProtocol;

import java.util.List;

public class StaircaseBuilder {
    private List<BatteryBank> banks;
    private EnergyProtocol protocol;

    public StaircaseBuilder from(List<BatteryBank> banks) {
        this.banks = banks;
        return this;
    }

    public StaircaseBuilder use(EnergyProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public StaircaseController build() {
        if (banks == null) throw new IllegalStateException("Missing data source (.from)");
        if (protocol == null) throw new IllegalStateException("Missing protocol (.use)");
        return new StaircaseController(banks, protocol);
    }
}
