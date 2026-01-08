package software.ulpgc.aoc.dia06.a;


import software.ulpgc.aoc.dia06.CargadorEntrada;
import software.ulpgc.aoc.dia06.ConstructorOperaciones;
import software.ulpgc.aoc.dia06.ControladorCompactador;

public class Main06A {

    public static void main(String[] args) {
        ConstructorOperaciones analizador = new AnalizadorVertical();
        ControladorCompactador controlador = CargadorEntrada.cargar("dia06input", analizador);

        long resultado = controlador.ejecutar();

        System.out.println("--- RESULTADO DÍA 6: COMPACTADOR DE BASURA ---");
        System.out.println("Suma total de todas las columnas: " + resultado);
    }
}