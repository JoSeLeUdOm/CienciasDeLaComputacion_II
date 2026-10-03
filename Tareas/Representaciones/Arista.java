// clase arista, la uso en la matriz y lista de incidencia
public class Arista {
    String id;
    String origen;
    String destino;
    double peso;

    public Arista(String id, String origen, String destino, double peso) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    // revisa si la arista conecta a con b
    boolean conecta(String a, String b, boolean dirigido) {
        if (dirigido) {
            // si es dirigido solo cuenta origen->destino
            if (origen.equals(a) && destino.equals(b)) {
                return true;
            }
            return false;
        } else {
            // si no es dirigido cuenta en los dos sentidos
            if (origen.equals(a) && destino.equals(b)) {
                return true;
            }
            if (origen.equals(b) && destino.equals(a)) {
                return true;
            }
            return false;
        }
    }

    // dice si el vertice v esta en la arista
    boolean incide(String v) {
        if (origen.equals(v) || destino.equals(v)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return id + "(" + origen + "-" + destino + ", " + peso + ")";
    }
}
