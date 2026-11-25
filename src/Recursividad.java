import java.util.ArrayList;
import java.util.List;

/**
 * ACTIVIDAD 1 - Algoritmos recursivos sobre la estructura jerárquica del campus.
 *
 * Ambos siguen el patrón "divide y vencerás": el problema sobre una zona se
 * divide en el mismo problema sobre cada una de sus subzonas.
 */
public class Recursividad {

    /**
     * Cuenta el total de aulas de una zona y de todas sus subzonas.
     *
     * Caso base     : zona sin subzonas -> devuelve sus aulas propias.
     * Caso recursivo: aulas propias + contarAulas(cada subzona).
     * Finalización  : cada llamada baja un nivel del árbol; como el árbol es
     *                 finito, se llega siempre a zonas sin hijas (caso base).
     */
    public static int contarAulas(Zona zona) {
        if (zona.getSubzonas().isEmpty()) {          // caso base
            return zona.getAulasPropias();
        }
        int total = zona.getAulasPropias();
        for (Zona hija : zona.getSubzonas()) {       // caso recursivo
            total += contarAulas(hija);
        }
        return total;
    }

    /**
     * Orientación jerárquica: construye el camino "Campus > Pabellón > Piso > Aula"
     * hasta una zona destino usando retroceso (backtracking).
     *
     * Caso base 1: la zona actual es el destino -> éxito (true).
     * Caso base 2: la zona no tiene hijas y no es el destino -> fracaso (false).
     * Caso recursivo: se prueba cada subzona; si ninguna lleva al destino,
     *                 se deshace el paso (remove) y se devuelve false.
     */
    public static boolean rutaJerarquica(Zona zona, String destino, List<String> camino) {
        camino.add(zona.getNombre());
        if (zona.getNombre().equalsIgnoreCase(destino)) {   // caso base: encontrado
            return true;
        }
        for (Zona hija : zona.getSubzonas()) {              // caso recursivo
            if (rutaJerarquica(hija, destino, camino)) {
                return true;
            }
        }
        camino.remove(camino.size() - 1);                   // retroceso
        return false;
    }

    /** Construye el campus de ejemplo usado en la demostración. */
    public static Zona construirCampus() {
        Zona pabA = new Zona("Pabellon A", 0)
                .agregar(new Zona("Piso 1 A", 6))
                .agregar(new Zona("Piso 2 A", 6))
                .agregar(new Zona("Piso 3 A", 4));
        Zona pabB = new Zona("Pabellon B", 0)
                .agregar(new Zona("Piso 1 B", 5))
                .agregar(new Zona("Piso 2 B", 5));
        Zona labs = new Zona("Laboratorios", 0)
                .agregar(new Zona("Lab Computo", 3))
                .agregar(new Zona("Lab IA", 2));
        return new Zona("Campus", 1)
                .agregar(pabA).agregar(pabB).agregar(labs);
    }

    public static List<String> nuevoCamino() {
        return new ArrayList<>();
    }
}
