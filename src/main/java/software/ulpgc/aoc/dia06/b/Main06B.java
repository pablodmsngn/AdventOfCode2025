package software.ulpgc.aoc.dia06.b;


import software.ulpgc.aoc.dia06.CargadorEntrada;
import software.ulpgc.aoc.dia06.ConstructorOperaciones;
import software.ulpgc.aoc.dia06.ControladorCompactador;

public class Main06B {
    public static void main(String[] args) {

        ConstructorOperaciones analizador = new AnalizadorCefalopodo();

        ControladorCompactador controlador = CargadorEntrada.cargar("dia06input", analizador);

        long resultado = controlador.ejecutar();

        System.out.println("--- DÍA 6 PARTE 2: MATEMÁTICAS CEFALÓPODAS ---");
        System.out.println("Total General: " + resultado);
    }
}