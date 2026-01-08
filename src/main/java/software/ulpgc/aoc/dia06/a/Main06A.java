package software.ulpgc.aoc.dia06.a;


import software.ulpgc.aoc.dia06.CargadorEntrada;
import software.ulpgc.aoc.dia06.ConstructorOperaciones;
import software.ulpgc.aoc.dia06.ControladorCompactador;

public class Main06A {

    public static void main(String[] args) {
        // 1. CONFIGURACIÓN (Inyección de Dependencia)
        // Usamos el AnalizadorVertical (la lógica del chico listo)
        ConstructorOperaciones analizador = new AnalizadorVertical();

        // 2. CARGA (Usando la fachada estilo adjunto)
        // Asegúrate de tener 'Day06Input.txt' en la carpeta resources
        ControladorCompactador controlador = CargadorEntrada.cargar("dia06input", analizador);

        // 3. EJECUCIÓN
        long resultado = controlador.ejecutar();

        System.out.println("--- RESULTADO DÍA 6: COMPACTADOR DE BASURA ---");
        System.out.println("Suma total de todas las columnas: " + resultado);
    }
}