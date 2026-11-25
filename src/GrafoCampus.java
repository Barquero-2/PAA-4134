import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ACTIVIDAD 3 - Grafo ponderado del campus, representado con LISTA DE ADYACENCIA.
 *
 * Nodos  : ubicaciones (Ubicacion).
 * Aristas: caminos peatonales; peso = distancia real en metros.
 * Sentido: no dirigido por defecto (se camina en ambos sentidos); también se
 *          admiten aristas dirigidas (p. ej. una rampa de un solo sentido).
 */
public class GrafoCampus {

    public static class Arista {
        public final String destino;
        public final double peso;
        public Arista(String destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    private final Map<String, Ubicacion> nodos = new LinkedHashMap<>();
    private final Map<String, List<Arista>> adyacencia = new LinkedHashMap<>();

    public void agregarNodo(Ubicacion u) {
        nodos.put(u.getNombre(), u);
        adyacencia.put(u.getNombre(), new ArrayList<>());
    }

    /** Arista bidireccional. Valida que el peso no sea menor a la línea recta
     *  (condición para que la heurística de A* sea admisible). */
    public void agregarArista(String a, String b, double peso) {
        agregarAristaDirigida(a, b, peso);
        agregarAristaDirigida(b, a, peso);
    }

    public void agregarAristaDirigida(String origen, String destino, double peso) {
        double recta = nodos.get(origen).distanciaA(nodos.get(destino));
        if (peso < recta) {
            throw new IllegalArgumentException("Peso menor que la línea recta entre " + origen + " y " + destino
                    + ": la heurística dejaría de ser admisible.");
        }
        adyacencia.get(origen).add(new Arista(destino, peso));
    }

    public Ubicacion getNodo(String nombre) { return nodos.get(nombre); }
    public Map<String, Ubicacion> getNodos() { return nodos; }
    public List<Arista> vecinos(String nombre) { return adyacencia.get(nombre); }

    public void imprimirListaAdyacencia() {
        for (String n : nodos.keySet()) {
            StringBuilder sb = new StringBuilder(String.format("  %-15s -> ", n));
            for (Arista a : adyacencia.get(n)) {
                sb.append(a.destino).append("(").append((int) a.peso).append(") ");
            }
            System.out.println(sb);
        }
    }

    /** Campus de ejemplo: 9 ubicaciones y 12 caminos. */
    public static GrafoCampus campusEjemplo() {
        GrafoCampus g = new GrafoCampus();
        g.agregarNodo(new Ubicacion("Entrada",        "ACCESO",    0,   0));
        g.agregarNodo(new Ubicacion("Biblioteca",     "SERVICIO", 100,  40));
        g.agregarNodo(new Ubicacion("Aulas_A",        "ACADEMICO", 60, 110));
        g.agregarNodo(new Ubicacion("Cafeteria",      "SERVICIO", 150, 100));
        g.agregarNodo(new Ubicacion("Auditorio",      "ACADEMICO",190,  20));
        g.agregarNodo(new Ubicacion("Laboratorios",   "ACADEMICO",230, 110));
        g.agregarNodo(new Ubicacion("Administracion", "ADMIN",     30, 190));
        g.agregarNodo(new Ubicacion("Aulas_B",        "ACADEMICO",170, 190));
        g.agregarNodo(new Ubicacion("Gimnasio",       "DEPORTE",  260, 200));

        g.agregarArista("Entrada", "Biblioteca", 115);
        g.agregarArista("Entrada", "Aulas_A", 130);
        g.agregarArista("Biblioteca", "Cafeteria", 85);
        g.agregarArista("Biblioteca", "Auditorio", 100);
        g.agregarArista("Aulas_A", "Cafeteria", 95);
        g.agregarArista("Aulas_A", "Administracion", 90);
        g.agregarArista("Cafeteria", "Laboratorios", 90);
        g.agregarArista("Cafeteria", "Aulas_B", 100);
        g.agregarArista("Auditorio", "Laboratorios", 105);
        g.agregarArista("Administracion", "Aulas_B", 150);
        g.agregarArista("Laboratorios", "Gimnasio", 100);
        g.agregarArista("Aulas_B", "Gimnasio", 95);
        return g;
    }
}
