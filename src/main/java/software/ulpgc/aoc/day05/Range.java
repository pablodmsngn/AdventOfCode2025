package software.ulpgc.aoc.day05;


public record Range(long min, long max) implements Comparable<Range> {


    public static Range fromText(String text) {
        String[] parts = text.split("-");
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public boolean contains(long id) {
        return id >= min && id <= max;
    }

    public long length() {
        return (max - min) + 1;
    }

    @Override
    public int compareTo(Range other) {
        return Long.compare(this.min, other.min);
    }


    public boolean overlapsWith(Range other) {
        return this.max >= other.min && this.min <= other.max;
    }

    public Range merge(Range other) {
        return new Range(
                Math.min(this.min, other.min),
                Math.max(this.max, other.max)
        );
    }
}