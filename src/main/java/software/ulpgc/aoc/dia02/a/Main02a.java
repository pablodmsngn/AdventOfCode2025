package software.ulpgc.aoc.dia02.a;

import software.ulpgc.aoc.dia02.CargadorEntrada;
import software.ulpgc.aoc.dia02.EstrategiaValidacion;
import software.ulpgc.aoc.dia02.Motor;
import java.util.function.LongPredicate;

public class Main02a {
    public static void main(String[] args) {
        // 1. Definir la Estrategia (La regla de negocio)
        // Buscamos IDs que NO cumplan el patrón repetido (ej: 123123 es inválido)
        LongPredicate estrategia = EstrategiaValidacion.PATRON_A;

        // 2. Uso de tu CargadorEntrada (Infraestructura)
        // Delegamos la lectura y construcción al cargador estático.
        // Asegúrate de que "Day02Input.txt" esté en la carpeta resources.
        Motor motor = CargadorEntrada.cargar("dia02Ainput", estrategia);

        // 3. Ejecución (Motor)
        long resultado = motor.run();

        System.out.println("--- RESULTADO DÍA 2 ---");
        System.out.println("Suma de IDs inválidos: " + resultado);
    }
}