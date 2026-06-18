package software.ulpgc.aoc.day12;
import java.util.List;

public class FarmController {
    private final List<ProblemDefinition> problems;

    public FarmController(List<ProblemDefinition> problems) {
        this.problems = problems;
    }

    public long countValidRegions() {
        return problems.stream()
                .filter(this::isSolvable)
                .count();
    }

    private boolean isSolvable(ProblemDefinition p) {
        return new FarmSolver().solve(p.region(), p.pieces());
    }
}