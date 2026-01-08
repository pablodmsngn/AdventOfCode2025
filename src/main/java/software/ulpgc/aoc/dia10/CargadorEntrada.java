package software.ulpgc.aoc.dia10;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CargadorEntrada {

    public static ControladorFabrica cargar(String fichero) {
        InputStream is = CargadorEntrada.class.getClassLoader().getResourceAsStream(fichero);
        if (is == null) throw new RuntimeException("Fichero no encontrado: " + fichero);
        return cargar(is);
    }

    public static ControladorFabrica cargar(InputStream entrada) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entrada))) {
            List<Maquina> maquinas = reader.lines()
                    .map(Maquina::desde)
                    .toList();
            return new ControladorFabrica(maquinas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}