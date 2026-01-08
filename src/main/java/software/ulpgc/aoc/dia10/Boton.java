package software.ulpgc.aoc.dia10;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public record Boton(Set<Integer> indicesAfectados) {
    //factory methor esta escrito como constrructor pero no es un constructor
    public static Boton desde(String str) {
        // Formato: (0,2,3) -> quitamos paréntesis y parseamos números
        return new Boton(Arrays.stream(str.substring(1, str.length() - 1).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet()));
    }
}