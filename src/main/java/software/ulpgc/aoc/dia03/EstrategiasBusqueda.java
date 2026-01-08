package software.ulpgc.aoc.dia03;

public class EstrategiasBusqueda {

    public static long Greedy(String secuencia, int n) {
        if (secuencia == null || n <= 0 || n > secuencia.length()) {
            return 0;
        }
        StringBuilder resultado = new StringBuilder();
        int ultimaPosicion = -1;
        for (int i = 0; i < n; i++) {
            int digitosFaltantes = n - 1 - i;
            int limiteBusqueda = secuencia.length() - digitosFaltantes;

            int mejorDigito = -1;
            int posicionMejorDigito = -1;

            for (int j = ultimaPosicion + 1; j < limiteBusqueda; j++) {
                int digitoActual = Character.getNumericValue(secuencia.charAt(j));


                if (digitoActual == 9) {
                    mejorDigito = 9;
                    posicionMejorDigito = j;
                    break;
                }

                if (digitoActual > mejorDigito) {
                    mejorDigito = digitoActual;
                    posicionMejorDigito = j;
                }
            }

            if (posicionMejorDigito != -1) {
                resultado.append(mejorDigito);
                ultimaPosicion = posicionMejorDigito;
            }
        }

        return !resultado.isEmpty() ? Long.parseLong(resultado.toString()) : 0L;
    }
}