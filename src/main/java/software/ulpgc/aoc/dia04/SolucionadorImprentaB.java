package software.ulpgc.aoc.dia04;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SolucionadorImprentaB implements Ejecutador {
    private final InputStream entrada;

    public SolucionadorImprentaB(InputStream entrada) {
        this.entrada = entrada;
    }

    @Override
    public long ejecutar() {
        // 1. Cargar estado inicial
        List<String> lineas = new BufferedReader(new InputStreamReader(entrada))
                .lines()
                .toList();

        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(lineas);

        long totalRetirados = 0;
        boolean hayCambios = true;

        // 2. Bucle de simulación
        while (hayCambios) {
            // Analizar el estado actual
            OptimizadorMontacargas optimizador = new OptimizadorMontacargas(almacen);

            // Obtener lista de lo que podemos sacar AHORA
            List<Coordenada> rollosAccesibles = optimizador.obtenerUbicacionesAccesibles().toList();

            if (rollosAccesibles.isEmpty()) {
                hayCambios = false; // Condición de parada
            } else {
                // Sumar al total
                totalRetirados += rollosAccesibles.size();

                // Actualizar el almacén retirando esos rollos (Genera nuevo estado)
                almacen = almacen.retirarRollos(rollosAccesibles);

                // El bucle se repite con el 'almacen' actualizado
            }
        }

        return totalRetirados;
    }
}
