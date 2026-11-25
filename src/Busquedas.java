import java.util.*;
import java.util.function.Predicate;

/**
 * ACTIVIDAD 3 - Algoritmos de búsqueda sobre el grafo: DFS, BFS y A*.
 */
public class Busquedas {

    /** Resultado de una búsqueda. */
    public static class Resultado {
        public List<String> camino = new ArrayList<>();      // ruta encontrada
        public List<String> exploracion = new ArrayList<>(); // orden en que se visitaron nodos
        public double costo = 0;                             // metros
        public boolean encontrado = false;
    }

    // ------------------------------------------------------------------ DFS
    /** DFS recursivo: baja por un camino hasta el fondo y retrocede si no hay salida. */
    public static Resultado dfs(GrafoCampus g, String origen, String destino) {
        Resultado r = new Resultado();
        Set<String> visitados = new HashSet<>();
        r.encontrado = dfsRec(g, origen, destino, visitados, r.camino, r.exploracion);
        r.costo = costoCamino(g, r.camino);
        return r;
    }

    private static boolean dfsRec(GrafoCampus g, String actual, String destino,
                                  Set<String> visitados, List<String> camino, List<String> orden) {
        visitados.add(actual);
        orden.add(actual);
        camino.add(actual);
        if (actual.equals(destino)) return true;                    // caso base: meta
        for (GrafoCampus.Arista a : g.vecinos(actual)) {
            if (!visitados.contains(a.destino)) {                   // evita ciclos
                if (dfsRec(g, a.destino, destino, visitados, camino, orden)) return true;
            }
        }
        camino.remove(camino.size() - 1);                           // retroceso
        return false;
    }

    /** Aplicación típica de DFS: ¿se puede llegar a todas las ubicaciones desde el origen? */
    public static Set<String> alcanzables(GrafoCampus g, String origen) {
        Set<String> visitados = new LinkedHashSet<>();
        explorar(g, origen, visitados);
        return visitados;
    }

    private static void explorar(GrafoCampus g, String actual, Set<String> visitados) {
        visitados.add(actual);
        for (GrafoCampus.Arista a : g.vecinos(actual)) {
            if (!visitados.contains(a.destino)) explorar(g, a.destino, visitados);
        }
    }

    // ------------------------------------------------------------------ BFS
    /** BFS por niveles con cola: garantiza el camino con MENOS aristas (saltos). */
    public static Resultado bfs(GrafoCampus g, String origen, Predicate<Ubicacion> esMeta) {
        Resultado r = new Resultado();
        Queue<String> cola = new LinkedList<>();
        Map<String, String> padre = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        cola.add(origen);
        visitados.add(origen);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            r.exploracion.add(actual);
            if (esMeta.test(g.getNodo(actual))) {
                r.encontrado = true;
                r.camino = reconstruir(padre, actual);
                r.costo = costoCamino(g, r.camino);
                return r;
            }
            for (GrafoCampus.Arista a : g.vecinos(actual)) {
                if (visitados.add(a.destino)) {
                    padre.put(a.destino, actual);
                    cola.add(a.destino);
                }
            }
        }
        return r;
    }

    public static Resultado bfs(GrafoCampus g, String origen, String destino) {
        return bfs(g, origen, u -> u.getNombre().equals(destino));
    }

    // ------------------------------------------------------------------- A*
    /**
     * A*: f(n) = g(n) + h(n)
     *   g(n) = costo real acumulado desde el origen.
     *   h(n) = estimación a la meta (distancia en línea recta, admisible).
     * Con usarHeuristica = false, h = 0 y se comporta como Dijkstra (sirve para comparar).
     */
    public static Resultado aEstrella(GrafoCampus g, String origen, String destino, boolean usarHeuristica) {
        Resultado r = new Resultado();
        Ubicacion meta = g.getNodo(destino);
        Map<String, Double> costoG = new HashMap<>();
        Map<String, String> padre = new HashMap<>();
        Set<String> cerrados = new HashSet<>();
        // Cola de prioridad ordenada por f = g + h
        PriorityQueue<Object[]> abiertos = new PriorityQueue<>(Comparator.comparingDouble(o -> (double) o[1]));

        costoG.put(origen, 0.0);
        abiertos.add(new Object[]{origen, heuristica(g.getNodo(origen), meta, usarHeuristica)});

        while (!abiertos.isEmpty()) {
            String actual = (String) abiertos.poll()[0];
            if (!cerrados.add(actual)) continue;   // ya expandido con mejor costo
            r.exploracion.add(actual);
            if (actual.equals(destino)) {
                r.encontrado = true;
                r.camino = reconstruir(padre, actual);
                r.costo = costoG.get(actual);
                return r;
            }
            for (GrafoCampus.Arista a : g.vecinos(actual)) {
                if (cerrados.contains(a.destino)) continue;
                double nuevoG = costoG.get(actual) + a.peso;
                if (nuevoG < costoG.getOrDefault(a.destino, Double.MAX_VALUE)) {
                    costoG.put(a.destino, nuevoG);
                    padre.put(a.destino, actual);
                    double f = nuevoG + heuristica(g.getNodo(a.destino), meta, usarHeuristica);
                    abiertos.add(new Object[]{a.destino, f});
                }
            }
        }
        return r;
    }

    private static double heuristica(Ubicacion n, Ubicacion meta, boolean usar) {
        return usar ? n.distanciaA(meta) : 0.0;
    }

    // ------------------------------------------------------------ utilidades
    private static List<String> reconstruir(Map<String, String> padre, String fin) {
        LinkedList<String> camino = new LinkedList<>();
        for (String n = fin; n != null; n = padre.get(n)) camino.addFirst(n);
        return camino;
    }

    public static double costoCamino(GrafoCampus g, List<String> camino) {
        double total = 0;
        for (int i = 0; i + 1 < camino.size(); i++) {
            for (GrafoCampus.Arista a : g.vecinos(camino.get(i))) {
                if (a.destino.equals(camino.get(i + 1))) { total += a.peso; break; }
            }
        }
        return total;
    }
}
