package software.ulpgc.aoc.dia10;


import java.util.List;

public enum Estado {
    ENCENDIDO, APAGADO;

    public static Estado deCaracter(char c) {
        return c == '#' ? ENCENDIDO : APAGADO;
    }

    public static List<Estado> parsear(String str) {
        return str.substring(1, str.length() - 1)
                .chars()
                .mapToObj(c -> deCaracter((char) c))
                .toList();
    }
}