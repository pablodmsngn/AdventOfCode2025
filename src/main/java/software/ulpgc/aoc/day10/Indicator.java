package software.ulpgc.aoc.day10;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record Indicator(List<State> states, List<Integer> voltages) {

    public static Indicator initial(int size, int voltageSize) {
        List<State> offStates = IntStream.range(0, size)
                .mapToObj(i -> State.OFF)
                .toList();
        List<Integer> zeroVoltages = IntStream.range(0, voltageSize)
                .map(i -> 0)
                .boxed()
                .toList();
        return new Indicator(offStates, zeroVoltages);
    }
    //PART2
    public Indicator createInitialState() {
        return initial(states.size(), voltages.size());
    }

    public Indicator reduceVoltagesWith(Indicator other) {
        List<Integer> newVoltages = IntStream.range(0, voltages.size())
                .mapToObj(i -> voltages.get(i) - other.voltages.get(i))
                .toList();
        return new Indicator(states, newVoltages);
    }


    public Indicator voltageHalf() {
        List<Integer> newVoltages = voltages.stream()
                .map(v -> v / 2)
                .toList();
        return new Indicator(states, newVoltages);
    }

    public State toggleState(int index) {
        return states.get(index) == State.ON ? State.OFF : State.ON;
    }

    public static Indicator from(String statePart, String voltagePart) {
        return new Indicator(State.parse(statePart), parseVoltages(voltagePart));
    }

    private static List<Integer> parseVoltages(String str) {
        return Arrays.stream(str.substring(1, str.length() - 1).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }


}