package software.ulpgc.aoc.dia05;


public record Rango(long min, long max) implements Comparable<Rango> {


    public static Rango desdeTexto(String texto) {
        String[] partes = texto.split("-");
        return new Rango(Long.parseLong(partes[0]), Long.parseLong(partes[1]));
    }

    public boolean contiene(long id) {
        return id >= min && id <= max;
    }

    public long longitud() {
        return (max - min) + 1;
    }

    @Override
    public int compareTo(Rango otro) {
        return Long.compare(this.min, otro.min);
    }


    public boolean solapaCon(Rango otro) {
        return this.max >= otro.min && this.min <= otro.max;
    }

    public Rango fusionar(Rango otro) {
        return new Rango(
                Math.min(this.min, otro.min),
                Math.max(this.max, otro.max)
        );
    }
}