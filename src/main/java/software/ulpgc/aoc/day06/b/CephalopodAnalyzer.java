package software.ulpgc.aoc.day06.b;

import software.ulpgc.aoc.day06.OperationBuilder;
import software.ulpgc.aoc.day06.Operation;
import software.ulpgc.aoc.day06.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// part2

public class CephalopodAnalyzer implements OperationBuilder {
    private final List<String> numericLines = new ArrayList<>();
    private String operatorLine;
    private int maxLength = 0;

    @Override
    public OperationBuilder addLine(String line) {
        if (line.length() > maxLength) maxLength = line.length();

        if (!line.isEmpty() && Operator.isOperator(line.charAt(0))) {
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

        List<Integer> indices = getOperatorIndices();

        return IntStream.range(0, indices.size())
                .mapToObj(i -> {
                    int start = indices.get(i);
                    int end = (i + 1 < indices.size()) ? indices.get(i + 1) - 1 : maxLength;
                    Operator op = Operator.from(String.valueOf(operatorLine.charAt(start)));
                    List<Long> operands = extractVerticalOperands(start, end, normalizedLines);
                    return new Operation(operands, op);
                });
    }

    private List<Long> extractVerticalOperands(int start, int end, List<String> lines) {
        List<Long> numbers = new ArrayList<>();
        for (int col = start; col < end; col++) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (col < line.length()) {
                    char c = line.charAt(col);
                    if (Character.isDigit(c)) {
                        sb.append(c);
                    }
                }
            }
            if (!sb.isEmpty()) {
                numbers.add(Long.parseLong(sb.toString()));
            }
        }
        return numbers;
    }

    private List<Integer> getOperatorIndices() {
        return IntStream.range(0, operatorLine.length())
                .filter(i -> Operator.isOperator(operatorLine.charAt(i)))
                .boxed()
                .toList();
    }

    private String fill(String str) {
        if (str.length() >= maxLength) return str;
        return String.format("%-" + maxLength + "s", str);
    }
}