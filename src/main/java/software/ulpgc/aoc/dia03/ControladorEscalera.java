package software.ulpgc.aoc.dia03;

import java.util.List;

public record ControladorEscalera(List<BancoBaterias> bancos, ProtocoloEnergia protocolo) {
    public long activar() {
        return bancos.stream()
                .mapToLong(b -> protocolo.calcularEnergia(b.secuencia()))
                .sum();
    }
}
