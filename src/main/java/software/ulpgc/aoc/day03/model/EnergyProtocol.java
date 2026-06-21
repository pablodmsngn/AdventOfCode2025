package software.ulpgc.aoc.day03.model;

@FunctionalInterface
public interface EnergyProtocol {
    long calculateEnergy(String sequence);
}
