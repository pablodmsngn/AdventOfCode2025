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
    public static final LongPredicate PATRON_B = id -> {
        // ^(\d+) -> Captura el grupo inicial
        // \1{2,} -> Busca que ese grupo se repita 2 veces o más (Total >= 3 instancias)
        // matches devuelve true si cumple el patrón.
        // negate (!) devuelve false, marcándolo como "inválido" para la tienda.
        return !Long.toString(id).matches("^(\\d+)\\1{2,}$");
    };
}
