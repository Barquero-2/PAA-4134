import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ACTIVIDAD 2 - Ordenamiento recursivo (Merge Sort) de ubicaciones del campus.
 * Complejidad: O(n log n) en todos los casos; es estable.
 */
public class MergeSort {

    public static boolean traza = false; // si es true, imprime las divisiones y mezclas

    public static List<Ubicacion> ordenar(List<Ubicacion> lista, Comparator<Ubicacion> criterio) {
        return ordenar(lista, criterio, 0);
    }

    private static List<Ubicacion> ordenar(List<Ubicacion> lista, Comparator<Ubicacion> criterio, int nivel) {
        // Caso base: 0 o 1 elemento ya está ordenado.
        if (lista.size() <= 1) {
            return new ArrayList<>(lista);
        }
        // DIVIDE: partir por la mitad.
        int medio = lista.size() / 2;
        List<Ubicacion> izq = new ArrayList<>(lista.subList(0, medio));
        List<Ubicacion> der = new ArrayList<>(lista.subList(medio, lista.size()));
        if (traza) System.out.println("  ".repeat(nivel) + "dividir  " + nombres(izq) + " | " + nombres(der));

        // VENCE: ordenar cada mitad recursivamente.
        List<Ubicacion> izqOrd = ordenar(izq, criterio, nivel + 1);
        List<Ubicacion> derOrd = ordenar(der, criterio, nivel + 1);

        // COMBINA: mezclar dos listas ya ordenadas.
        List<Ubicacion> resultado = mezclar(izqOrd, derOrd, criterio);
        if (traza) System.out.println("  ".repeat(nivel) + "mezclar  -> " + nombres(resultado));
        return resultado;
    }

    private static List<Ubicacion> mezclar(List<Ubicacion> a, List<Ubicacion> b, Comparator<Ubicacion> c) {
        List<Ubicacion> res = new ArrayList<>(a.size() + b.size());
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            if (c.compare(a.get(i), b.get(j)) <= 0) res.add(a.get(i++)); // <= mantiene estabilidad
            else res.add(b.get(j++));
        }
        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) res.add(b.get(j++));
        return res;
    }

    private static String nombres(List<Ubicacion> l) {
        List<String> n = new ArrayList<>();
        for (Ubicacion u : l) n.add(u.getNombre());
        return n.toString();
    }
}
