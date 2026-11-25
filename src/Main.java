import java.util.*;

/**
 * Módulo inteligente de orientación del campus - demostración completa.
 * Compilar:  javac -d out src/*.java      Ejecutar:  java -cp out Main
 */
public class Main {

    public static void main(String[] args) {
        actividad1();
        actividad2();
        actividad3y4();
    }

    // ------------------------------------------------------------ Actividad 1
    static void actividad1() {
        titulo("ACTIVIDAD 1 - Recursividad aplicada (estructura jerarquica)");
        Zona campus = Recursividad.construirCampus();
        System.out.println("Total de aulas en el campus: " + Recursividad.contarAulas(campus));
        for (Zona z : campus.getSubzonas()) {
            System.out.println("  " + z.getNombre() + ": " + Recursividad.contarAulas(z) + " aulas");
        }
        List<String> camino = Recursividad.nuevoCamino();
        boolean ok = Recursividad.rutaJerarquica(campus, "Lab IA", camino);
        System.out.println("Ruta jerarquica hasta 'Lab IA' (" + ok + "): " + String.join(" > ", camino));
    }

    // ------------------------------------------------------------ Actividad 2
    static void actividad2() {
        titulo("ACTIVIDAD 2 - Ordenamiento recursivo (Merge Sort)");
        GrafoCampus g = GrafoCampus.campusEjemplo();
        List<Ubicacion> lista = new ArrayList<>(g.getNodos().values());
        Ubicacion usuario = g.getNodo("Entrada");

        System.out.println("Traza del ordenamiento alfabetico (6 primeras ubicaciones):");
        MergeSort.traza = true;
        MergeSort.ordenar(lista.subList(0, 6), Comparator.comparing(Ubicacion::getNombre));
        MergeSort.traza = false;

        System.out.println("\nDirectorio alfabetico:");
        for (Ubicacion u : MergeSort.ordenar(lista, Comparator.comparing(Ubicacion::getNombre)))
            System.out.println("  " + u);

        System.out.println("\nUbicaciones ordenadas por cercania (linea recta) a la Entrada:");
        for (Ubicacion u : MergeSort.ordenar(lista, Comparator.comparingDouble(u -> u.distanciaA(usuario))))
            System.out.printf("  %-15s %6.1f m%n", u.getNombre(), u.distanciaA(usuario));
    }

    // -------------------------------------------------------- Actividades 3 y 4
    static void actividad3y4() {
        titulo("ACTIVIDAD 3 - Modelado con grafos");
        GrafoCampus g = GrafoCampus.campusEjemplo();
        System.out.println("Lista de adyacencia (destino(metros)):");
        g.imprimirListaAdyacencia();

        String origen = "Entrada", destino = "Gimnasio";

        System.out.println("\nDFS - conectividad del campus desde " + origen + ":");
        Set<String> alc = Busquedas.alcanzables(g, origen);
        System.out.println("  Alcanzables: " + alc.size() + " de " + g.getNodos().size()
                + (alc.size() == g.getNodos().size() ? " -> campus totalmente conexo" : " -> hay zonas aisladas"));

        System.out.println("\nBFS - servicio mas cercano (por cantidad de tramos) desde Administracion:");
        Busquedas.Resultado rs = Busquedas.bfs(g, "Administracion", u -> u.getTipo().equals("SERVICIO"));
        imprimir(rs);

        titulo("COMPARACION DE RUTAS: " + origen + " -> " + destino);
        System.out.println("DFS");              imprimir(Busquedas.dfs(g, origen, destino));
        System.out.println("BFS");              imprimir(Busquedas.bfs(g, origen, destino));
        System.out.println("A* (sin heuristica = Dijkstra)");
        imprimir(Busquedas.aEstrella(g, origen, destino, false));
        System.out.println("A* (con heuristica)");
        imprimir(Busquedas.aEstrella(g, origen, destino, true));

        titulo("A* en otra consulta: Administracion -> Laboratorios");
        System.out.println("Sin heuristica"); imprimir(Busquedas.aEstrella(g, "Administracion", "Laboratorios", false));
        System.out.println("Con heuristica"); imprimir(Busquedas.aEstrella(g, "Administracion", "Laboratorios", true));
    }

    static void imprimir(Busquedas.Resultado r) {
        System.out.println("  Ruta      : " + String.join(" -> ", r.camino));
        System.out.printf("  Distancia : %.0f m | Tramos: %d | Nodos explorados: %d%n",
                r.costo, Math.max(0, r.camino.size() - 1), r.exploracion.size());
        System.out.println("  Orden de exploracion: " + r.exploracion);
    }

    static void titulo(String t) {
        System.out.println("\n==================================================================");
        System.out.println(t);
        System.out.println("==================================================================");
    }
}
