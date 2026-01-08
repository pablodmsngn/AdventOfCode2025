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

    public Rectangulo encontrarMasGrandePermitido() {
        List<Rectangulo> bordesPoligono = obtenerBordesPoligono();
        return generarRectangulos()
                .filter(r -> esRectanguloValido(r, bordesPoligono))
                .findFirst()
                .orElse(new Rectangulo(new Coordenada(0, 0), new Coordenada(0, 0)));
    }

    private boolean esRectanguloValido(Rectangulo r, List<Rectangulo> bordes) {
        for (Rectangulo borde : bordes) {
            if (corta(borde, r)) return false;
        }
        double centroX = (r.c1().x() + r.c2().x()) / 2.0;
        double centroY = (r.c1().y() + r.c2().y()) / 2.0;
        return puntoEstaDentro(centroX, centroY, bordes);
    }

    private boolean corta(Rectangulo borde, Rectangulo r) {
        if (borde.esVertical()) {
            long x = borde.c1().x();
            boolean xDentro = x > r.minX() && x < r.maxX();
            boolean ySolapa = Math.max(borde.minY(), r.minY()) < Math.min(borde.maxY(), r.maxY());
            return xDentro && ySolapa;
        } else {
            long y = borde.c1().y();
            boolean yDentro = y > r.minY() && y < r.maxY();
            boolean xSolapa = Math.max(borde.minX(), r.minX()) < Math.min(borde.maxX(), r.maxX());
            return yDentro && xSolapa;
        }
    }

    private boolean puntoEstaDentro(double x, double y, List<Rectangulo> bordes) {
        int intersecciones = 0;
        for (Rectangulo borde : bordes) {
            if (borde.esVertical()) {
                if (borde.c1().x() < x) {
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