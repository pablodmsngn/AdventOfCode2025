package software.ulpgc.aoc.dia04;


import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;

/**
 * Representa el mapa completo del almacén.
 * * TRUCO DE INGENIERÍA:
 * Esta clase añade automáticamente un borde de seguridad (padding) de puntos '.'
 * ¿Para qué? Para que al pedir los vecinos de una esquina (0,0), no salte un
 * IndexOutOfBoundsException. Los vecinos fuera del mapa simplemente serán "VACIO".
 */
public record CuadriculaAlmacen(List<List<ContenidoCasilla>> rejilla) {

    public ContenidoCasilla contenidoEn(Coordenada coord) {
        return rejilla.get(coord.fila() + 1).get(coord.columna() + 1);
    }

    private int ancho() {

        return rejilla.get(0).size() - 2;
    }

    private int alto() {
        return rejilla.size() - 2;
    }

    // parrte2
    /**
     * Crea una NUEVA cuadrícula con los rollos indicados eliminados (puestos a VACIO).
     * Mantiene la inmutabilidad: no modifica la actual, devuelve una copia modificada.
     */
    public CuadriculaAlmacen retirarRollos(List<Coordenada> aRetirar) {
        // 1. Crear una copia profunda mutable de la rejilla actual
        List<List<ContenidoCasilla>> nuevaRejilla = new ArrayList<>();
        for (List<ContenidoCasilla> fila : this.rejilla) {
            nuevaRejilla.add(new ArrayList<>(fila));
        }

        // 2. Aplicar los cambios
        for (Coordenada c : aRetirar) {
            // +1 por el padding de seguridad
            nuevaRejilla.get(c.fila() + 1).set(c.columna() + 1, ContenidoCasilla.VACIO);
        }

        // 3. Devolver el nuevo estado
        return new CuadriculaAlmacen(nuevaRejilla);
    }


    public static CuadriculaAlmacen desde(List<String> mapaBruto) {
        // se hacambiado .getFirst() por .get(0) java21
        int anchoFila = mapaBruto.get(0).length();

        return new CuadriculaAlmacen(agregarRellenoSeguridad(mapaBruto.stream(), anchoFila)
                .map(CuadriculaAlmacen::rellenarFila)
                .map(CuadriculaAlmacen::parsearFila)
                .toList());
    }



    private static List<ContenidoCasilla> parsearFila(String fila) {
        return fila.chars()
                .mapToObj(c -> (char) c)
                .map(ContenidoCasilla::desdeCaracter)
                .toList();
    }

    private static Stream<String> agregarRellenoSeguridad(Stream<String> datos, int ancho) {
        String filaVacia = ".".repeat(ancho);
        return Stream.concat(Stream.of(filaVacia), Stream.concat(datos, Stream.of(filaVacia)));
    }

    private static String rellenarFila(String fila) {
        return "." + fila + ".";
    }

    public Stream<Coordenada> todasLasCoordenadas() {
        return IntStream.range(0, alto())
                .mapToObj(fila -> IntStream.range(0, ancho())
                        .mapToObj(col -> new Coordenada(fila, col)))
                .flatMap(s -> s);
    }
}