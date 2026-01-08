package software.ulpgc.aoc.dia10;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record Indicador(List<Estado> estados, List<Integer> voltajes) {

    public static Indicador inicial(int tamaño, int tamañoVoltajes) {
        List<Estado> estadosApagados = IntStream.range(0, tamaño)
                .mapToObj(i -> Estado.APAGADO)
                .toList();
        List<Integer> voltajesCero = IntStream.range(0, tamañoVoltajes)
                .map(i -> 0)
                .boxed()
                .toList();
        return new Indicador(estadosApagados, voltajesCero);
    }
    //PARTE2
    public Indicador crearEstadoInicial() {
        return inicial(estados.size(), voltajes.size());
    }

    public Indicador reducirVoltajesCon(Indicador otro) {
        List<Integer> nuevosVoltajes = IntStream.range(0, voltajes.size())
                .mapToObj(i -> voltajes.get(i) - otro.voltajes.get(i))
                .toList();
        return new Indicador(estados, nuevosVoltajes);
    }


    public Indicador mitadVoltajes() {
        List<Integer> nuevosVoltajes = voltajes.stream()
                .map(v -> v / 2)
                .toList();
        return new Indicador(estados, nuevosVoltajes);
    }

    public Estado alternarEstado(int indice) {
        return estados.get(indice) == Estado.ENCENDIDO ? Estado.APAGADO : Estado.ENCENDIDO;
    }

    public static Indicador desde(String parteEstados, String parteVoltajes) {
        return new Indicador(Estado.parsear(parteEstados), parsearVoltajes(parteVoltajes));
    }

    private static List<Integer> parsearVoltajes(String str) {
        return Arrays.stream(str.substring(1, str.length() - 1).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }


}