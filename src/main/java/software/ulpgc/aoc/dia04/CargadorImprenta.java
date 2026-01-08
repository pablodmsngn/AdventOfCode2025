package software.ulpgc.aoc.dia04;

import java.io.InputStream;

/**
 * Fachada Estática.
 * Simplifica la vida al 'Main'. Se encarga de buscar el archivo en la carpeta 'resources'
 * y llamar a la Fábrica. Centraliza el manejo de errores de "archivo no encontrado".
 */
public class CargadorImprenta {

    public static Ejecutador cargar(String nombreArchivo, FabricaEjecutador.TipoEjecutor tipo) {
        // Busca el archivo dentro del classpath (carpeta src/main/resources)
        InputStream stream = CargadorImprenta.class.getClassLoader().getResourceAsStream(nombreArchivo);

        if (stream == null) {
            throw new RuntimeException("Fichero no encontrado: " + nombreArchivo);
        }

        // Delega la creación a la fábrica
        return new FabricaEjecutador()
                .desde(stream)
                .tipo(tipo)
                .construir();
    }
}