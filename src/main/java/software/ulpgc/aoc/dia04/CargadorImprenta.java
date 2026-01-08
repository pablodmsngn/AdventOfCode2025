package software.ulpgc.aoc.dia04;

import java.io.InputStream;


public class CargadorImprenta {

    public static Ejecutador cargar(String nombreArchivo, FabricaEjecutador.TipoEjecutor tipo) {
        InputStream stream = CargadorImprenta.class.getClassLoader().getResourceAsStream(nombreArchivo);
        if (stream == null) {
            throw new RuntimeException("Fichero no encontrado: " + nombreArchivo);
        }
        return new FabricaEjecutador()
                .desde(stream)
                .tipo(tipo)
                .construir();
    }
}