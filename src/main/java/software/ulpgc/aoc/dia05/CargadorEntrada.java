package software.ulpgc.aoc.dia05;


import java.io.InputStream;

public class CargadorEntrada {

    public static AuditorInventario cargar(String filename, ProtocoloFrescura protocolo) {
        InputStream inputStream = CargadorEntrada.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("Fichero no encontrado: " + filename);
        }
        return new ConstructorAuditoria()
                .desde(inputStream)
                .usando(protocolo)
                .construir();
    }
}
