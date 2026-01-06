package software.ulpgc.aoc.dia02;

import java.util.function.LongPredicate;

public class EstrategiaValidacion {

    /**
     * Estrategia A: Detectar IDs formados por una secuencia repetida (ej: 123123).
     * Usamos una expresión regular para validar el patrón.
     */
    public static final LongPredicate PATRON_A = id -> {
        // La regex ^(\d+)\1$ busca un grupo de dígitos que se repite exactamente una vez.
        // negate() se usará después porque buscamos los que CUMPLEN esto (que son los "inválidos" para la tienda)
        return !Long.toString(id).matches("^(\\d+)\\1$");
    };
}
