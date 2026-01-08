package software.ulpgc.aoc.dia06.b;


import software.ulpgc.aoc.dia06.CargadorEntrada;
import software.ulpgc.aoc.dia06.ConstructorOperaciones;
import software.ulpgc.aoc.dia06.ControladorCompactador;

public class Main06B {
    public static void main(String[] args) {
        // 1. ESTRATEGIA: Usamos el Analizador tipo "Smart Kid" (Vertical)
        ConstructorOperaciones analizador = new AnalizadorCefalopodo();

        // 2. CARGA: Fachada que oculta la complejidad del InputStream
        ControladorCompactador controlador = CargadorEntrada.cargar("dia06input", analizador);

        // 3. EJECUCIÓN
        long resultado = controlador.ejecutar();

        System.out.println("--- DÍA 6 PARTE 2: MATEMÁTICAS CEFALÓPODAS ---");
        System.out.println("Total General: " + resultado);
    }
}