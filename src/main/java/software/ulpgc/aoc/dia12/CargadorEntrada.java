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
            List<String> lineas = reader.lines().toList();
            return procesar(lineas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ControladorGranja procesar(List<String> lineas) {
        Map<Integer, Forma> catalogoFormas = new HashMap<>();
        List<DefinicionProblema> problemas = new ArrayList<>();

        boolean leyendoFormas = true;
        int idActual = -1;
        List<Coordenada> puntosActuales = new ArrayList<>();
        int filaForma = 0;

        for (String linea : lineas) {
            if (linea.isBlank()) continue;

            if (Character.isDigit(linea.charAt(0)) && linea.contains(":")) {
                if (linea.contains("x")) { // Cambio a sección de regiones
                    if (leyendoFormas && idActual != -1) {
                        catalogoFormas.put(idActual, new Forma(idActual, new ArrayList<>(puntosActuales)));
                        idActual = -1;
                        puntosActuales.clear();
                    }
                    leyendoFormas = false;
                } else { // Nueva forma
                    if (idActual != -1) {
                        catalogoFormas.put(idActual, new Forma(idActual, new ArrayList<>(puntosActuales)));
                    }
                    idActual = Integer.parseInt(linea.replace(":", "").trim());
                    puntosActuales.clear();
                    filaForma = 0;
                    continue;
                }
            }

            if (leyendoFormas) {
                for (int c = 0; c < linea.length(); c++) {
                    if (linea.charAt(c) == '#') {
                        puntosActuales.add(new Coordenada(filaForma, c));
                    }
                }
                filaForma++;
            } else {
                problemas.add(parsearProblema(linea, catalogoFormas));
            }
        }

        if (leyendoFormas && idActual != -1) {
            catalogoFormas.put(idActual, new Forma(idActual, new ArrayList<>(puntosActuales)));
        }

        return new ControladorGranja(problemas);
    }

    private static DefinicionProblema parsearProblema(String linea, Map<Integer, Forma> catalogo) {
        String[] partes = linea.split(":");
        String[] dims = partes[0].trim().split("x");
        int w = Integer.parseInt(dims[0]);
        int h = Integer.parseInt(dims[1]);

        String[] cuentas = partes[1].trim().split("\\s+");
        List<Forma> piezas = new ArrayList<>();

        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].isBlank()) continue;
            int cantidad = Integer.parseInt(cuentas[i]);

            if (cantidad > 0) {
                Forma prototipo = catalogo.get(i);
                if (prototipo == null) throw new RuntimeException("Forma ID=" + i + " no encontrada.");
                for (int k = 0; k < cantidad; k++) {
                    piezas.add(prototipo);
                }
            }
        }
        return new DefinicionProblema(w, h, piezas);
    }
}