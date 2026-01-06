package software.ulpgc.aoc.dia03;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


public class ConstructorEscalera {
    private InputStream file;
    private ProtocoloEnergia protocolo;

    public ConstructorEscalera from(InputStream file) {
        this.file = file;
        return this;
    }

    public ConstructorEscalera use(ProtocoloEnergia protocolo) {
        this.protocolo = protocolo;
        return this;
    }

    public ControladorEscalera construir() {
        if (file == null) throw new IllegalStateException("Falta fuente de datos (.desde)");
        if (protocolo == null) throw new IllegalStateException("Falta protocolo (.usando)");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            List<BancoBaterias> bancos = reader.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(BancoBaterias::new)
                    .collect(Collectors.toList());

            return new ControladorEscalera(bancos, protocolo);
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo input", e);
        }
    }



}
