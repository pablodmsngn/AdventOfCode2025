package software.ulpgc.aoc.dia05;

import java.util.List;

@FunctionalInterface
public interface ProtocoloFrescura {
    boolean esFresco(long idIngrediente, List<Rango> rangos);
}
