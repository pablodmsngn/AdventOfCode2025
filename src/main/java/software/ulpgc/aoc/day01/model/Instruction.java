package software.ulpgc.aoc.day01.model;

import java.util.Optional;


public record Instruction(char direction, int quantity) {

    public static Optional<Instruction> of(String raw) {
        if (raw == null) return Optional.empty();
        String s = raw.trim();
        if (s.isEmpty()) return Optional.empty();

        char direction = s.charAt(0);
        if (direction != 'L' && direction != 'R') return Optional.empty();

        try {
            int quantity = Integer.parseInt(s.substring(1));
            return Optional.of(new Instruction(direction, quantity));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /** Desplazamiento con signo: L resta, R suma. */
    public int movement() {
        return direction == 'L' ? -quantity : quantity;
    }
}
