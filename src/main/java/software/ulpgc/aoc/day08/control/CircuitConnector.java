package software.ulpgc.aoc.day08.control;

import software.ulpgc.aoc.day08.model.Box;
import software.ulpgc.aoc.day08.model.BoxPair;
import software.ulpgc.aoc.day08.model.Circuit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CircuitConnector {
    private final List<Circuit> initialCircuits;

    public CircuitConnector(List<Circuit> circuits) {
        this.initialCircuits = circuits;
    }


    public Stream<BoxPair> getShortestConnections(List<Box> boxes, long limit) {
        return connectionFlow(boxes).limit(limit);
    }

    public Stream<BoxPair> getAllConnections(List<Box> boxes) {
        return connectionFlow(boxes);
    }

    private Stream<BoxPair> connectionFlow(List<Box> boxes) {
        return IntStream.range(0, boxes.size())
                .parallel()
                .mapToObj(i -> IntStream.range(i + 1, boxes.size())
                        .mapToObj(boxes::get)
                        .map(box2 -> {
                            Box box1 = boxes.get(i);
                            return new BoxPair(box1, box2, box1.distanceTo(box2));
                        })
                ).flatMap(s -> s)
                .sorted(Comparator.comparingDouble(BoxPair::distance));
    }


    public long calculateSafetyFactor(long n) {
        List<Box> allBoxes = getAllBoxes();
        ArrayList<Circuit> circuits = new ArrayList<>(this.initialCircuits);

        getShortestConnections(allBoxes, n)
                .sequential()
                .forEachOrdered(pair -> mergeCircuits(circuits, pair));

        return circuits.stream()
                .map(c -> c.boxes().size())
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(i -> i)
                .reduce(1, (a, b) -> a * b);
    }

    public long calculateMergeCost() {
        List<Box> allBoxes = getAllBoxes();
        ArrayList<Circuit> circuits = new ArrayList<>(this.initialCircuits);

        AtomicReference<BoxPair> mergingPair = new AtomicReference<>();
        getAllConnections(allBoxes)
                .sequential()
                .forEachOrdered(pair -> {
                    if (circuits.size() == 1) return;
                    boolean merged = mergeCircuits(circuits, pair);
                    if (merged && circuits.size() == 1) {
                        mergingPair.set(pair);
                    }
                });

        BoxPair last = mergingPair.get();
        if (last == null) throw new RuntimeException("Could not merge the circuits.");

        return (long) last.box1().x() * last.box2().x();
    }

    private boolean mergeCircuits(List<Circuit> circuits, BoxPair pair) {
        Circuit c1 = findCircuit(pair.box1(), circuits);

        if (c1.boxes().contains(pair.box2())) return false;

        Circuit c2 = findCircuit(pair.box2(), circuits);

        circuits.remove(c1);
        circuits.remove(c2);

        Set<Box> newBoxes = Stream.concat(c1.boxes().stream(), c2.boxes().stream())
                .collect(Collectors.toSet());
        circuits.add(new Circuit(newBoxes));
        return true;
    }

    private Circuit findCircuit(Box box, List<Circuit> circuits) {
        for (Circuit circuit : circuits) {
            if (circuit.boxes().contains(box)) return circuit;
        }
        throw new RuntimeException("Orphan box not found: " + box);
    }

    private List<Box> getAllBoxes() {
        return initialCircuits.stream()
                .flatMap(c -> c.boxes().stream())
                .toList();
    }
}
