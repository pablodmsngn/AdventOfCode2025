package software.ulpgc.aoc.day06;


import java.util.stream.Stream;

public class CompactorController {
    private final Stream<Operation> operations;

    public CompactorController(Stream<Operation> operations) {
        this.operations = operations;
    }

    public long execute() {
        return operations.mapToLong(Operation::calculate).sum();
    }
}
