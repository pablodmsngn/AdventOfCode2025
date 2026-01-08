package software.ulpgc.aoc.dia06;


import java.util.stream.Stream;


public interface ConstructorOperaciones {
    ConstructorOperaciones agregarLinea(String linea);
    Stream<Operacion> construir();
}
