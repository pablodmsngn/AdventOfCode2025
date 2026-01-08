package software.ulpgc.aoc.dia12;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CargadorEntrada {

    public static ControladorGranja cargar(String fichero) {
        InputStream is = CargadorEntrada.class.getClassLoader().getResourceAsStream(fichero);
        if (is == null) throw new RuntimeException("Fichero no encontrado: " + fichero);
        return cargar(is);
    }

    public static ControladorGranja cargar(InputStream entrada) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entrada))) {
            return procesar(reader.lines().toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ControladorGranja procesar(List<String> lineas) {
        Map<Integer, Forma> catalogo = new HashMap<>();
        List<DefinicionProblema> problemas = new ArrayList<>();

        int idActual = -1;
        List<String> bufferForma = new ArrayList<>();

        for (String linea : lineas) {
            if (linea.isBlank()) continue;

            if (linea.contains(":")) {
                if (idActual != -1 && !bufferForma.isEmpty()) {
                    catalogo.put(idActual, crearForma(idActual, bufferForma));
                    bufferForma.clear();
                }

                if (linea.contains("x")) {
                    idActual = -1;
                    problemas.add(parsearProblema(linea, catalogo));
                } else {
                    idActual = Integer.parseInt(linea.replace(":", "").trim());
                }
            } else if (idActual != -1) {
                bufferForma.add(linea);
            }
        }

        if (idActual != -1 && !bufferForma.isEmpty()) {
            catalogo.put(idActual, crearForma(idActual, bufferForma));
        }

        return new ControladorGranja(problemas);
    }

    private static Forma crearForma(int id, List<String> filas) {
        List<Coordenada> puntos = new ArrayList<>();
        for (int r = 0; r < filas.size(); r++) {
            String fila = filas.get(r);
            for (int c = 0; c < fila.length(); c++) {
                if (fila.charAt(c) == '#') puntos.add(new Coordenada(r, c));
            }
        }
        return new Forma(id, puntos);
    }

    private static DefinicionProblema parsearProblema(String linea, Map<Integer, Forma> catalogo) {
        String[] partes = linea.split(":");
        String[] dims = partes[0].trim().split("x");
        int w = Integer.parseInt(dims[0]);
        int h = Integer.parseInt(dims[1]);

        List<Forma> piezas = new ArrayList<>();
        String[] cuentas = partes[1].trim().split("\\s+");

        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].isBlank()) continue;
            int cantidad = Integer.parseInt(cuentas[i]);
            if (cantidad > 0) {
                Forma prototipo = catalogo.get(i);
                if (prototipo == null) throw new RuntimeException("Error: La forma ID=" + i + " no ha sido definida antes de usarse.");
                for (int k = 0; k < cantidad; k++) piezas.add(prototipo);
            }
        }
        return new DefinicionProblema(new Region(w, h), piezas);
    }
}