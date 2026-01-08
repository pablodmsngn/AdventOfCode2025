package software.ulpgc.aoc.dia06.a;


import software.ulpgc.aoc.dia06.ConstructorOperaciones;
import software.ulpgc.aoc.dia06.Operacion;
import software.ulpgc.aoc.dia06.Operador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//parte1


public class AnalizadorVertical implements ConstructorOperaciones {
    private List<String> lineasNumericas;
    private String lineaOperadores;
    private int longitudMaxima = 0;

    public AnalizadorVertical() {
        this.lineasNumericas = new ArrayList<>();
    }

    @Override
    public ConstructorOperaciones agregarLinea(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return this;
        }
        if (linea.length() > longitudMaxima) longitudMaxima = linea.length();
        if (Operador.esOperador(linea.charAt(0))) {
            lineaOperadores = linea;
        } else {
            lineasNumericas.add(linea);
        }
        return this;
    }

    @Override
    public Stream<Operacion> construir() {
        List<String> lineasNormalizadas = lineasNumericas.stream()
                .map(this::rellenar)
                .toList();
        List<Integer> indices = obtenerIndicesInicio();
        return generarOperaciones(obtenerListaOperadores(indices), obtenerListasOperandos(indices, lineasNormalizadas));
    }

    private List<List<Long>> obtenerListasOperandos(List<Integer> indices, List<String> lineas) {
        return IntStream.range(0, indices.size())
                .mapToObj(i -> lineas.stream()
                        .map(linea -> linea.substring(
                                indices.get(i),
                                i + 1 < indices.size() ? indices.get(i + 1) : longitudMaxima
                        ).strip())
                        .filter(s -> !s.isEmpty())
                        .map(Long::parseLong)
                        .toList()
                ).toList();
    }

    private List<Operador> obtenerListaOperadores(List<Integer> indices) {
        return indices.stream()
                .map(indice -> Operador.desde(String.valueOf(lineaOperadores.charAt(indice))))
                .toList();
    }

    private List<Integer> obtenerIndicesInicio() {
        if (lineaOperadores == null) return List.of();
        return IntStream.range(0, lineaOperadores.length())
                .filter(i -> Operador.esOperador(lineaOperadores.charAt(i)))
                .boxed()
                .toList();
    }

    private Stream<Operacion> generarOperaciones(List<Operador> operadores, List<List<Long>> operandos) {
        return IntStream.range(0, operadores.size())
                .mapToObj(i -> new Operacion(operandos.get(i), operadores.get(i)));
    }

    private String rellenar(String str) {
        if (str.length() >= longitudMaxima) return str;
        return String.format("%-" + longitudMaxima + "s", str);
    }
}