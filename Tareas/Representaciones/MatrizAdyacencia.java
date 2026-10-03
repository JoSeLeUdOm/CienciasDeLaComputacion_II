import java.util.ArrayList;
import java.util.List;

// Matriz de adyacencia
// la celda [i][j] guarda el peso de la arista de i a j, si es null no hay arista
public class MatrizAdyacencia implements Grafo {

    private boolean dirigido;
    private List<String> vertices = new ArrayList<>();
    private List<List<Double>> matriz = new ArrayList<>();

    public MatrizAdyacencia(boolean dirigido) {
        this.dirigido = dirigido;
    }

    // busca la posicion de un vertice en la lista
    private int indice(String v) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(v)) {
                return i;
            }
        }
        return -1;
    }

    public boolean agregarVertice(String v) {
        if (indice(v) >= 0) {
            return false; // ya existe
        }
        // le agrego una columna nueva a cada fila que ya existe
        for (int i = 0; i < matriz.size(); i++) {
            matriz.get(i).add(null);
        }
        vertices.add(v);
        // creo la fila nueva llena de null
        List<Double> nuevaFila = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            nuevaFila.add(null);
        }
        matriz.add(nuevaFila);
        return true;
    }

    public boolean eliminarVertice(String v) {
        int i = indice(v);
        if (i < 0) {
            return false;
        }
        matriz.remove(i); // quito la fila
        // quito la columna i de todas las filas
        for (int f = 0; f < matriz.size(); f++) {
            matriz.get(f).remove(i);
        }
        vertices.remove(i);
        return true;
    }

    public boolean modificarVertice(String viejo, String nuevo) {
        int i = indice(viejo);
        if (i < 0 || indice(nuevo) >= 0) {
            return false;
        }
        vertices.set(i, nuevo);
        return true;
    }

    public boolean agregarArista(String origen, String destino, double peso) {
        int i = indice(origen);
        int j = indice(destino);
        if (i < 0 || j < 0 || i == j) {
            return false;
        }
        if (matriz.get(i).get(j) != null) {
            return false; // ya hay arista
        }
        matriz.get(i).set(j, peso);
        if (!dirigido) {
            matriz.get(j).set(i, peso);
        }
        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        int i = indice(origen);
        int j = indice(destino);
        if (i < 0 || j < 0) {
            return false;
        }
        if (matriz.get(i).get(j) == null) {
            return false;
        }
        matriz.get(i).set(j, null);
        if (!dirigido) {
            matriz.get(j).set(i, null);
        }
        return true;
    }

    public boolean modificarArista(String origen, String destino, double nuevoPeso) {
        int i = indice(origen);
        int j = indice(destino);
        if (i < 0 || j < 0) {
            return false;
        }
        if (matriz.get(i).get(j) == null) {
            return false;
        }
        matriz.get(i).set(j, nuevoPeso);
        if (!dirigido) {
            matriz.get(j).set(i, nuevoPeso);
        }
        return true;
    }

    public void mostrar() {
        System.out.println("--- Matriz de adyacencia ---");
        if (vertices.isEmpty()) {
            System.out.println("(vacio)");
            return;
        }
        System.out.printf("%8s", "");
        for (int j = 0; j < vertices.size(); j++) {
            System.out.printf("%8s", vertices.get(j));
        }
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%8s", vertices.get(i));
            for (int j = 0; j < vertices.size(); j++) {
                Double p = matriz.get(i).get(j);
                if (p == null) {
                    System.out.printf("%8s", "-");
                } else {
                    System.out.printf("%8s", p.toString());
                }
            }
            System.out.println();
        }
    }
}
