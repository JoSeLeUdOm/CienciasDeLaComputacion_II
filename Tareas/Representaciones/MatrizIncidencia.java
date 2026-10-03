import java.util.ArrayList;
import java.util.List;

// Matriz de incidencia
// las filas son los vertices y las columnas son las aristas
// si no es dirigido pongo 1 en el origen y el destino
// si es dirigido pongo 1 en el origen y -1 en el destino
public class MatrizIncidencia implements Grafo {

    private boolean dirigido;
    private List<String> vertices = new ArrayList<>();
    private List<Arista> aristas = new ArrayList<>();
    private List<List<Integer>> matriz = new ArrayList<>();
    private int contador = 1;

    public MatrizIncidencia(boolean dirigido) {
        this.dirigido = dirigido;
    }

    private int indice(String v) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(v)) {
                return i;
            }
        }
        return -1;
    }

    // devuelve la posicion de la arista entre o y d, o -1 si no esta
    private int buscarArista(String o, String d) {
        for (int k = 0; k < aristas.size(); k++) {
            if (aristas.get(k).conecta(o, d, dirigido)) {
                return k;
            }
        }
        return -1;
    }

    private void eliminarColumna(int k) {
        aristas.remove(k);
        for (int f = 0; f < matriz.size(); f++) {
            matriz.get(f).remove(k);
        }
    }

    public boolean agregarVertice(String v) {
        if (indice(v) >= 0) {
            return false;
        }
        vertices.add(v);
        // la fila nueva empieza en 0 en todas las aristas
        List<Integer> fila = new ArrayList<>();
        for (int k = 0; k < aristas.size(); k++) {
            fila.add(0);
        }
        matriz.add(fila);
        return true;
    }

    public boolean eliminarVertice(String v) {
        int i = indice(v);
        if (i < 0) {
            return false;
        }
        // primero quito las aristas que tocan ese vertice (de atras para adelante)
        for (int k = aristas.size() - 1; k >= 0; k--) {
            if (aristas.get(k).incide(v)) {
                eliminarColumna(k);
            }
        }
        matriz.remove(i);
        vertices.remove(i);
        return true;
    }

    public boolean modificarVertice(String viejo, String nuevo) {
        int i = indice(viejo);
        if (i < 0 || indice(nuevo) >= 0) {
            return false;
        }
        vertices.set(i, nuevo);
        // actualizo el nombre en las aristas tambien
        for (int k = 0; k < aristas.size(); k++) {
            Arista a = aristas.get(k);
            if (a.origen.equals(viejo)) {
                a.origen = nuevo;
            }
            if (a.destino.equals(viejo)) {
                a.destino = nuevo;
            }
        }
        return true;
    }

    public boolean agregarArista(String origen, String destino, double peso) {
        int i = indice(origen);
        int j = indice(destino);
        if (i < 0 || j < 0 || i == j) {
            return false;
        }
        if (buscarArista(origen, destino) >= 0) {
            return false;
        }
        aristas.add(new Arista("e" + contador, origen, destino, peso));
        contador = contador + 1;
        // agrego la columna nueva a cada fila
        for (int r = 0; r < matriz.size(); r++) {
            int valor = 0;
            if (r == i) {
                valor = 1;
            }
            if (r == j) {
                if (dirigido) {
                    valor = -1;
                } else {
                    valor = 1;
                }
            }
            matriz.get(r).add(valor);
        }
        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        int k = buscarArista(origen, destino);
        if (k < 0) {
            return false;
        }
        eliminarColumna(k);
        return true;
    }

    public boolean modificarArista(String origen, String destino, double nuevoPeso) {
        int k = buscarArista(origen, destino);
        if (k < 0) {
            return false;
        }
        aristas.get(k).peso = nuevoPeso;
        return true;
    }

    public void mostrar() {
        System.out.println("--- Matriz de incidencia ---");
        if (vertices.isEmpty()) {
            System.out.println("(vacio)");
            return;
        }
        System.out.printf("%8s", "");
        for (int k = 0; k < aristas.size(); k++) {
            System.out.printf("%6s", aristas.get(k).id);
        }
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%8s", vertices.get(i));
            for (int k = 0; k < aristas.size(); k++) {
                System.out.printf("%6d", matriz.get(i).get(k));
            }
            System.out.println();
        }
        // muestro que significa cada arista
        for (int k = 0; k < aristas.size(); k++) {
            Arista a = aristas.get(k);
            String flecha;
            if (dirigido) {
                flecha = " -> ";
            } else {
                flecha = " - ";
            }
            System.out.println(a.id + " = " + a.origen + flecha + a.destino + " (peso " + a.peso + ")");
        }
    }
}
