package software.ulpgc.aoc.dia05;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorAuditoria {
    private List<String> lineas;
    private ProtocoloFrescura protocolo;

    public ConstructorAuditoria desde(InputStream stream) {
        this.lineas = new BufferedReader(new InputStreamReader(stream))
                .lines()
                .collect(Collectors.toList());
        return this;
    }

    public ConstructorAuditoria usando(ProtocoloFrescura protocolo) {
        this.protocolo = protocolo;
        return this;
    }

    public AuditorInventario construir() {
        List<Rango> rangos = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        boolean leyendoRangos = true;
        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.isEmpty()) {
                leyendoRangos = false;
                continue;
            }
            if (leyendoRangos) {
                rangos.add(Rango.desdeTexto(linea));
            } else {
                ids.add(Long.parseLong(linea));
            }
        }
        return new AuditorInventario(rangos, ids, protocolo);
    }
}