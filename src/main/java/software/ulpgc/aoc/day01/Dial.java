package software.ulpgc.aoc.day01;



public record Dial(int position) {
    public Dial() {
        this(50);
    }
    public Dial {
        position = normalize(position);
    }
    public Dial rotate(int quantity) {
        return new Dial(this.position + quantity);
    }
    private static int normalize(int n) {
        return ((n % 100) + 100) % 100;
    }
}

