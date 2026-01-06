package software.ulpgc.aoc.dia02;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.function.LongPredicate;
import java.util.stream.Stream;

public class CargadorEntrada {
    public static Motor cargar(String filename, LongPredicate validator) {
        try {
            InputStream inputStream = CargadorEntrada.class.getClassLoader().getResourceAsStream(filename);

            if (inputStream == null) {
                throw new RuntimeException("Archivo no encontrado en resources: " + filename);
            }

            // Lógica específica del Día 2: El archivo es una sola línea separada por comas
            // Usamos BufferedReader para leer y Stream para procesar
            Stream<RangoID> ranges = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .flatMap(line -> Arrays.stream(line.split(","))) // Separar por comas
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(RangoID::new); // Convertir cada trozo "11-22" en un objeto RangoID

            // Construimos el motor inyectándole los datos y la estrategia
            return new Motor(ranges, validator);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando entrada desde " + filename, e);
        }
    }
}