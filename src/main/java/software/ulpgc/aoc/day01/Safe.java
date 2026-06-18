package software.ulpgc.aoc.day01;


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
        if (instruction == null || instruction.trim().isEmpty()) return;
        char direction = instruction.charAt(0);
        int quantity = Integer.parseInt(instruction.substring(1));
        int movement = (direction == 'L') ? -quantity : quantity;
        Dial nextDial = dial.rotate(movement);
        this.zeroCount += protocol.calculatePoints(this.dial, movement, nextDial);
        this.dial = nextDial;
    }





    public int getZeroCount() {
        return zeroCount;
    }


}
