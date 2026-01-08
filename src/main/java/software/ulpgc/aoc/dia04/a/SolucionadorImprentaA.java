package software.ulpgc.aoc.dia04.a;

import software.ulpgc.aoc.dia04.CuadriculaAlmacen;
import software.ulpgc.aoc.dia04.Ejecutador;
import software.ulpgc.aoc.dia04.OptimizadorMontacargas;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Implementación concreta para resolver la PARTE A del problema.
 * Responsabilidad: Unir la lectura del fichero con la lógica del Optimizador.
 */
public class SolucionadorImprentaA implements Ejecutador {
    private final InputStream entrada;

    public SolucionadorImprentaA(InputStream entrada) {
        this.entrada = entrada;
    }

    @Override
    public long ejecutar() {
        // 1. Leer el fichero y convertirlo a lista de líneas
        List<String> lineas = new BufferedReader(new InputStreamReader(entrada))
                .lines()
                .toList();

        // 2. Crear el modelo de dominio (El almacén inteligente)
        CuadriculaAlmacen almacen = CuadriculaAlmacen.desde(lineas);

        // 3. Instanciar el algoritmo con los datos cargados
        OptimizadorMontacargas optimizador = new OptimizadorMontacargas(almacen);

        // 4. Ejecutar y devolver el resultado
        return optimizador.contarRollosAccesibles();
    }
}