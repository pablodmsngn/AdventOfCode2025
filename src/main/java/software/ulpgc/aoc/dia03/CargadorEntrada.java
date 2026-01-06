package software.ulpgc.aoc.dia03;

import java.io.InputStream;

public class CargadorEntrada {

    public static ControladorEscalera cargar(String filename, ProtocoloEnergia protocolo) {
        InputStream inputStream = CargadorEntrada.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("Fichero no encontrado: " + filename);
        }

        // DRY: Reutilizamos el Builder
        return new ConstructorEscalera()
                .from(inputStream)
                .use(protocolo)
                .construir();
    }
}
