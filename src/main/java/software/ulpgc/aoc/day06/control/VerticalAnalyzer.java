package software.ulpgc.aoc.day06.control;

import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.model.OperationBuilder;
import software.ulpgc.aoc.day06.model.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// part1
public class VerticalAnalyzer implements OperationBuilder {
    private final List<String> numericLines;
    private String operatorLine;
    private int maxLength = 0;

    public VerticalAnalyzer() {
        this.numericLines = new ArrayList<>();
    }

    @Override
    public OperationBuilder addLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return this;
        }
        if (line.length() > maxLength) maxLength = line.length();
        if (Operator.isOperator(line.charAt(0))) {
            operatorLine = line;
        } else {
            numericLines.add(line);
        }
        return this;
    }

    @Override
    public Stream<Operation> build() {
        List<String> normalizedLines = numericLines.stream()
                .map(this::fill)
                .toList();
        List<Integer> indices = getStartIndices();
        return generateOperations(getOperatorList(indices), getOperandLists(indices, normalizedLines));
    }

    private List<List<Long>> getOperandLists(List<Integer> indices, List<String> lines) {
        return IntStream.range(0, indices.size())
                .mapToObj(i -> lines.stream()
                        .map(line -> line.substring(
                                indices.get(i),
                                i + 1 < indices.size() ? indices.get(i + 1) : maxLength
                        ).strip())
                        .filter(s -> !s.isEmpty())
                        .map(Long::parseLong)
                        .toList()
                ).toList();
    }

    private List<Operator> getOperatorList(List<Integer> indices) {
        return indices.stream()
                .map(index -> Operator.from(String.valueOf(operatorLine.charAt(index))))
                .toList();
    }

    private List<Integer> getStartIndices() {
        if (operatorLine == null) return List.of();
        return IntStream.range(0, operatorLine.length())
                .filter(i -> Operator.isOperator(operatorLine.charAt(i)))
                .boxed()
                .toList();
    }

    private Stream<Operation> generateOperations(List<Operator> operators, List<List<Long>> operands) {
        return IntStream.range(0, operators.size())
                .mapToObj(i -> new Operation(operands.get(i), operators.get(i)));
    }

    private String fill(String str) {
        if (str.length() >= maxLength) return str;
        return String.format("%-" + maxLength + "s", str);
    }
}
