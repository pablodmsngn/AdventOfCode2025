package software.ulpgc.aoc.dia09;


import java.util.List;

public class ControladorCine {
    private final List<Coordenada> baldosasRojas;

    public ControladorCine(List<Coordenada> baldosasRojas) {
        this.baldosasRojas = baldosasRojas;
    }

    public long obtenerAreaMaxima() {
        return new BuscadorRectangulos(baldosasRojas)
                .encontrarMasGrande()
                .area();
    }
    //parte2
    public long obtenerAreaMaximaPermitida() {
        return new BuscadorRectangulos(baldosasRojas).encontrarMasGrandePermitido().area();
    }
}