package software.ulpgc.aoc.dia09;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record BuscadorRectangulos(List<Coordenada> baldosasRojas) {

    public Stream<Rectangulo> generarRectangulos() {
        return IntStream.range(0, baldosasRojas.size() - 1)
                .mapToObj(i -> IntStream.range(i + 1, baldosasRojas.size())
                        .mapToObj(j -> new Rectangulo(baldosasRojas.get(i), baldosasRojas.get(j))))
                .flatMap(s -> s)
                .sorted(Comparator.comparingLong(Rectangulo::area).reversed());
    }

    public Rectangulo encontrarMasGrande() {
        return generarRectangulos()
                .findFirst()
                .orElse(new Rectangulo(new Coordenada(0, 0), new Coordenada(0, 0)));
    }

    // OPTIMIZACIÓN: Geometría en lugar de fuerza bruta
    public Rectangulo encontrarMasGrandePermitido() {
        // 1. Construimos los bordes del polígono linealmente (O(N))
        List<Rectangulo> bordesPoligono = obtenerBordesPoligono();

        // 2. Filtramos usando intersección geométrica (muy rápido)
        return generarRectangulos()
                .filter(r -> esRectanguloValido(r, bordesPoligono))
                .findFirst()
                .orElse(new Rectangulo(new Coordenada(0, 0), new Coordenada(0, 0)));
    }

    private boolean esRectanguloValido(Rectangulo r, List<Rectangulo> bordes) {
        // A. Comprobación rápida: ¿Algún borde del polígono CORTA al rectángulo?
        // Si un borde cruza a través del rectángulo, entonces el rectángulo se sale del área permitida.
        for (Rectangulo borde : bordes) {
            if (corta(borde, r)) return false;
        }

        // B. Si nadie lo corta, solo necesitamos saber si está DENTRO o FUERA.
        // Comprobamos un solo punto (el centro, o una esquina ligeramente desplazada).
        // Usamos el punto medio para asegurar que estamos en el interior.
        double centroX = (r.c1().x() + r.c2().x()) / 2.0;
        double centroY = (r.c1().y() + r.c2().y()) / 2.0;

        return puntoEstaDentro(centroX, centroY, bordes);
    }

    // Comprueba si un borde (línea) atraviesa el interior del rectángulo
    private boolean corta(Rectangulo borde, Rectangulo r) {
        if (borde.esVertical()) {
            long x = borde.c1().x();
            // El borde vertical está entre la izquierda y derecha del rectángulo (exclusivo)
            boolean xDentro = x > r.minX() && x < r.maxX();
            // Y las alturas se solapan
            boolean ySolapa = Math.max(borde.minY(), r.minY()) < Math.min(borde.maxY(), r.maxY());
            return xDentro && ySolapa;
        } else {
            long y = borde.c1().y();
            // El borde horizontal está entre arriba y abajo del rectángulo (exclusivo)
            boolean yDentro = y > r.minY() && y < r.maxY();
            // Y las anchuras se solapan
            boolean xSolapa = Math.max(borde.minX(), r.minX()) < Math.min(borde.maxX(), r.maxX());
            return yDentro && xSolapa;
        }
    }

    // Ray-Casting simplificado para un punto decimal (el centro)
    private boolean puntoEstaDentro(double x, double y, List<Rectangulo> bordes) {
        int intersecciones = 0;
        for (Rectangulo borde : bordes) {
            // Solo miramos bordes verticales para el rayo horizontal hacia la izquierda
            if (borde.esVertical()) {
                // Si el borde está a nuestra izquierda
                if (borde.c1().x() < x) {
                    // Y nuestra Y está dentro del rango vertical del borde
                    if (y >= borde.minY() && y <= borde.maxY()) {
                        intersecciones++;
                    }
                }
            }
        }
        return intersecciones % 2 != 0;
    }

    private List<Rectangulo> obtenerBordesPoligono() {
        List<Rectangulo> bordes = new ArrayList<>();
        int n = baldosasRojas.size();
        for (int i = 0; i < n; i++) {
            Coordenada actual = baldosasRojas.get(i);
            Coordenada siguiente = baldosasRojas.get((i + 1) % n);
            bordes.add(new Rectangulo(actual, siguiente));
        }
        return bordes;
    }
}