package software.ulpgc.aoc.dia06.b;

import software.ulpgc.aoc.dia06.ConstructorOperaciones;
import software.ulpgc.aoc.dia06.Operacion;
import software.ulpgc.aoc.dia06.Operador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//parte2

public class AnalizadorCefalopodo implements ConstructorOperaciones {
    private final List<String> lineasNumericas = new ArrayList<>();
    private String lineaOperadores;
    private int longitudMaxima = 0;

    @Override
    public ConstructorOperaciones agregarLinea(String linea) {
        if (linea.length() > longitudMaxima) longitudMaxima = linea.length();

        if (!linea.isEmpty() && Operador.esOperador(linea.charAt(0))) {
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

        List<Integer> indices = obtenerIndicesOperadores();

        return IntStream.range(0, indices.size())
                .mapToObj(i -> {
                    int inicio = indices.get(i);
                    int fin = (i + 1 < indices.size()) ? indices.get(i + 1) - 1 : longitudMaxima;
                    Operador op = Operador.desde(String.valueOf(lineaOperadores.charAt(inicio)));
                    List<Long> operandos = extraerOperandosVerticales(inicio, fin, lineasNormalizadas);
                    return new Operacion(operandos, op);
                });
    }

    private List<Long> extraerOperandosVerticales(int inicio, int fin, List<String> lineas) {
        List<Long> numeros = new ArrayList<>();
        for (int col = inicio; col < fin; col++) {
            StringBuilder sb = new StringBuilder();
            for (String linea : lineas) {
                if (col < linea.length()) {
                    char c = linea.charAt(col);
                    if (Character.isDigit(c)) {
                        sb.append(c);
                    }
                }
            }
            if (!sb.isEmpty()) {
                numeros.add(Long.parseLong(sb.toString()));
            }
        }
        return numeros;
    }

    private List<Integer> obtenerIndicesOperadores() {
        return IntStream.range(0, lineaOperadores.length())
                .filter(i -> Operador.esOperador(lineaOperadores.charAt(i)))
                .boxed()
                .toList();
    }

    private String rellenar(String str) {
        if (str.length() >= longitudMaxima) return str;
        return String.format("%-" + longitudMaxima + "s", str);
    }
}