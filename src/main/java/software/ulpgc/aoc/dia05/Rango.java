package software.ulpgc.aoc.dia05;


public record Rango(long min, long max) implements Comparable<Rango> {


    public static Rango desdeTexto(String texto) {
        String[] partes = texto.split("-");
        return new Rango(Long.parseLong(partes[0]), Long.parseLong(partes[1]));
    }

    public boolean contiene(long id) {
        return id >= min && id <= max;
    }

    // Calcula cuántos números hay en este rango
    public long longitud() {
        return (max - min) + 1;
    }

    // Ordenar por inicio de rango (necesario para el algoritmo de fusión)
    @Override
    public int compareTo(Rango otro) {
        return Long.compare(this.min, otro.min);
    }

    // Comprueba si se solapa con otro rango (ej: 10-14 y 12-18)
    public boolean solapaCon(Rango otro) {
        // Se solapan si:
        // 1. Mi final es posterior a tu inicio (this.max >= otro.min)
        // 2. Y mi inicio es anterior a tu final (this.min <= otro.max)
        return this.max >= otro.min && this.min <= otro.max;
    }

    // Crea un nuevo rango fusionado (ej: 10-14 + 12-18 = 10-18)
    public Rango fusionar(Rango otro) {
        return new Rango(
                Math.min(this.min, otro.min),
                Math.max(this.max, otro.max)
        );
    }
}