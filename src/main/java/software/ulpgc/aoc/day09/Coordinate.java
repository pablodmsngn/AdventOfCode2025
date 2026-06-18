package software.ulpgc.aoc.day09;


import java.util.Arrays;

public record Coordinate(long x, long y) {
    public static Coordinate from(String str) {
        long[] nums = Arrays.stream(str.split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
        return new Coordinate(nums[0], nums[1]);
    }
}