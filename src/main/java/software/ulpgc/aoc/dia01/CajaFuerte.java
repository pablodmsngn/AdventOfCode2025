package software.ulpgc.aoc.dia01;


public class CajaFuerte {
    private final ProtocoloSeguridad protocolo;
    private Dial dial;
    private int vecesEnCero;

    public CajaFuerte(ProtocoloSeguridad protocolo) {
        this.dial = new Dial();
        this.vecesEnCero = 0;
        this.protocolo = protocolo;
    }

    public void rotar(String instruccion) {
        if (instruccion == null || instruccion.trim().isEmpty()) return;
        char direccion = instruccion.charAt(0);
        int cantidad = Integer.parseInt(instruccion.substring(1));
        int movimiento = (direccion == 'L') ? -cantidad : cantidad;
        Dial suguienteDial = dial.rotar(movimiento);
        this.vecesEnCero += protocolo.calculatePoints(this.dial, movimiento, suguienteDial);
        this.dial = suguienteDial;
    }


    public int getVecesCero() {
        return vecesEnCero;
    }


}
