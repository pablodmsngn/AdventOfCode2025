package software.ulpgc.aoc.day10;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Machine(Indicator target, List<Button> buttons) {

    // Part 1
    public int solveMinPresses() {
        List<Set<Button>> solutions = findLightSolutions(target);
        return solutions.isEmpty() ? 0 : solutions.get(0).size();
    }

    // Part 2
    public int solveVoltageRequirements(Map<Indicator, Integer> cache) {
        if (cache.containsKey(target)) return cache.get(target);

        if (target.voltages().stream().allMatch(v -> v == 0)) {
            return 0;
        }

        Indicator parityIndicator = convertVoltageToLights(target);
        Machine intermediateMachine = new Machine(parityIndicator, buttons);
        List<Set<Button>> paritySolutions = intermediateMachine.findLightSolutions(parityIndicator);

        int minCoste = 99999;

        for (Set<Button> solution : paritySolutions) {
            Indicator effect = applyButtons(parityIndicator.createInitialState(), solution);
            Indicator rest = target.reduceVoltagesWith(effect);

            if (rest.voltages().stream().anyMatch(v -> v < 0)) continue;

            Indicator newTarget = rest.voltageHalf();

            Machine nextMachine = new Machine(newTarget, buttons);
            int recursiveCost = nextMachine.solveVoltageRequirements(cache);

            if (recursiveCost < 99999) {
                int totalCost = solution.size() + 2 * recursiveCost;
                if (totalCost < minCoste) {
                    minCoste = totalCost;
                }
            }
        }

        cache.put(target, minCoste);
        return minCoste;
    }


    private List<Set<Button>> findLightSolutions(Indicator target) {
        List<Set<Button>> solutions = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        Queue<Long> queue = new ArrayDeque<>();

        queue.add(0L);
        visited.add(0L);

        Indicator base = target.createInitialState();
        List<State> targetStates = target.states();

        while (!queue.isEmpty()) {
            long mask = queue.poll();

            if (calculateOnlyStates(base, mask).equals(targetStates)) {
                solutions.add(convertMaskToSet(mask));
            }

            for (int i = 0; i < buttons.size(); i++) {
                if ((mask & (1L << i)) != 0) continue;

                long newMask = mask | (1L << i);

                if (visited.add(newMask)) {
                    queue.add(newMask);
                }
            }
        }
        return solutions;
    }

    private List<State> calculateOnlyStates(Indicator base, long mask) {
        return IntStream.range(0, base.states().size())
                .mapToObj(i -> {
                    boolean initialOn = (base.states().get(i) == State.ON);
                    long toggleCount = 0;
                    for (int b = 0; b < buttons.size(); b++) {
                        if ((mask & (1L << b)) != 0) { // If button 'b' is pressed
                            if (buttons.get(b).affectedIndices().contains(i)) {
                                toggleCount++;
                            }
                        }
                    }
                    boolean change = (toggleCount % 2 != 0);
                    boolean onResult = change ? !initialOn : initialOn;
                    return onResult ? State.ON : State.OFF;
                })
                .toList();
    }

    private Set<Button> convertMaskToSet(long mask) {
        Set<Button> set = new HashSet<>();
        for (int i = 0; i < buttons.size(); i++) {
            if ((mask & (1L << i)) != 0) {
                set.add(buttons.get(i));
            }
        }
        return set;
    }

    private Indicator convertVoltageToLights(Indicator ind) {
        List<State> newStates = IntStream.range(0, ind.states().size())
                .mapToObj(i -> ind.voltages().get(i) % 2 != 0 ? State.ON : State.OFF)
                .toList();
        return new Indicator(newStates, ind.createInitialState().voltages());
    }

    private Indicator applyButtons(Indicator currentState, Set<Button> buttons) {
        return buttons.stream().reduce(currentState, this::applyButton, (a, b) -> b);
    }

    private Indicator applyButton(Indicator state, Button button) {
        List<State> newStates = IntStream.range(0, state.states().size())
                .mapToObj(i -> button.affectedIndices().contains(i)
                        ? state.toggleState(i)
                        : state.states().get(i))
                .toList();

        List<Integer> newVoltages = IntStream.range(0, state.voltages().size())
                .mapToObj(i -> state.voltages().get(i) + (button.affectedIndices().contains(i) ? 1 : 0))
                .toList();

        return new Indicator(newStates, newVoltages);
    }

    public static Machine from(String line) {
        String[] parts = line.split(" ");
        Indicator target = Indicator.from(parts[0], parts[parts.length - 1]);
        List<Button> buttons = Arrays.stream(parts, 1, parts.length - 1).map(Button::from).toList();
        return new Machine(target, buttons);
    }
}