package software.ulpgc.aoc.dia08;

import java.util.List;

public class ControladorLuces {
    private final List<Circuito> circuitos;

    public ControladorLuces(List<Circuito> circuitos) {
        this.circuitos = circuitos;
    }


    public long ejecutar(long numeroDeConexiones) {
        return new ConectorCircuitos(circuitos).calcularFactorSeguridad(numeroDeConexiones);
    }
    //parte2
    public long ejecutarUnificacion() {
        return new ConectorCircuitos(circuitos).calcularCosteUnificacion();
    }
}