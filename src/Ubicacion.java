/**
 * Nodo del grafo: un lugar del campus con coordenadas (en metros) para poder
 * calcular la heurística de A*.
 */
public class Ubicacion {
    private final String nombre;
    private final String tipo; // ACCESO, ACADEMICO, SERVICIO, ADMIN, DEPORTE
    private final double x;
    private final double y;

    public Ubicacion(String nombre, String tipo, double x, double y) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public double getX() { return x; }
    public double getY() { return y; }

    /** Distancia euclidiana (línea recta) a otra ubicación. */
    public double distanciaA(Ubicacion otra) {
        double dx = x - otra.x;
        double dy = y - otra.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return nombre + " [" + tipo + "]";
    }
}
