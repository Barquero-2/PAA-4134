import java.util.ArrayList;
import java.util.List;

/**
 * Estructura jerárquica del campus (Campus > Pabellón > Piso > Aula).
 * Es una estructura recursiva por naturaleza: una zona contiene otras zonas.
 */
public class Zona {
    private final String nombre;
    private final int aulasPropias; // aulas que pertenecen directamente a esta zona
    private final List<Zona> subzonas = new ArrayList<>();

    public Zona(String nombre, int aulasPropias) {
        this.nombre = nombre;
        this.aulasPropias = aulasPropias;
    }

    public Zona agregar(Zona hija) {
        subzonas.add(hija);
        return this;
    }

    public String getNombre() { return nombre; }
    public int getAulasPropias() { return aulasPropias; }
    public List<Zona> getSubzonas() { return subzonas; }
}
