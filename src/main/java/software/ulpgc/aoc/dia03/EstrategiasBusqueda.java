package software.ulpgc.aoc.dia03;

public class EstrategiasBusqueda {

    /**
     * Algoritmo Greedy para encontrar el mayor número formado por 'n' dígitos.
     * @param secuencia La cadena de baterías (ej: "98765...")
     * @param n Cantidad de dígitos a buscar (ej: 2 para el Plan A)
     */
    public static long Greedy(String secuencia, int n) {
        if (secuencia == null || n <= 0 || n > secuencia.length()) {
            return 0;
        }

        StringBuilder resultado = new StringBuilder();
        int ultimaPosicion = -1;

        // Buscamos N veces el mejor dígito posible
        for (int i = 0; i < n; i++) {
            int digitosFaltantes = n - 1 - i;
            int limiteBusqueda = secuencia.length() - digitosFaltantes;

            int mejorDigito = -1;
            int posicionMejorDigito = -1;

            // Recorremos la ventana válida a la derecha de la última posición
            for (int j = ultimaPosicion + 1; j < limiteBusqueda; j++) {
                int digitoActual = Character.getNumericValue(secuencia.charAt(j));

                // Optimización Greedy: Si es 9, es insuperable
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