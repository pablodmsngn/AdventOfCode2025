package software.ulpgc.aoc.dia09;


import java.util.Arrays;

public record Coordenada(long x, long y) {
    public static Coordenada desde(String str) {
        long[] nums = Arrays.stream(str.split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
        return new Coordenada(nums[0], nums[1]);
    }
}