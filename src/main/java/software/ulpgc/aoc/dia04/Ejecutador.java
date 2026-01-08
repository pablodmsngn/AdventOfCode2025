package software.ulpgc.aoc.dia04;

/**
 * Interfaz común para los solucionadores
 * Define el contrato que deben cumplir tanto la Parte A como la Parte B.
 */
@FunctionalInterface
public interface Ejecutador {
    long ejecutar();
}