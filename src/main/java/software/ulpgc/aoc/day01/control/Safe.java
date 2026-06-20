package software.ulpgc.aoc.day01.control;

import software.ulpgc.aoc.day01.model.Dial;
import software.ulpgc.aoc.day01.model.Instruction;
import software.ulpgc.aoc.day01.model.SecurityProtocol;


public class Safe {
    private final SecurityProtocol protocol;
    private Dial dial;
    private int zeroCount;

    public Safe(SecurityProtocol protocol) {
        this.dial = new Dial();
        this.zeroCount = 0;
        this.protocol = protocol;
    }


    public void rotate(String instruction) {
        Instruction.of(instruction).ifPresent(this::apply);
    }


    public void apply(Instruction instruction) {
        int movement = instruction.movement();
        Dial nextDial = dial.rotate(movement);
        this.zeroCount += protocol.calculatePoints(this.dial, movement, nextDial);
        this.dial = nextDial;
    }

    public int getZeroCount() {
        return zeroCount;
    }
}
