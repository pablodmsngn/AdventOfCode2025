package software.ulpgc.aoc.day06;


import java.util.stream.Stream;


public interface OperationBuilder {
    OperationBuilder addLine(String line);
    Stream<Operation> build();
}
