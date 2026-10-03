import java.util.LinkedHashMap;
import java.util.Map;

// Lista de adyacencia
// cada vertice guarda un mapa con sus vecinos y el peso de la arista
public class ListaAdyacencia implements Grafo {

    private boolean dirigido;
    private Map<String, Map<String, Double>> lista = new LinkedHashMap<>();

    public ListaAdyacencia(boolean dirigido) {
        this.dirigido = dirigido;
    }

    public boolean agregarVertice(String v) {
        if (lista.containsKey(v)) {
            return false;
        }
        lista.put(v, new LinkedHashMap<>());
        return true;
    }

    public boolean eliminarVertice(String v) {
        if (!lista.containsKey(v)) {
            return false;
        }
        lista.remove(v);
        // tambien lo tengo que quitar de los vecinos de los demas
        for (Map<String, Double> vecinos : lista.values()) {
            vecinos.remove(v);
        }
        return true;
    }

    public boolean modificarVertice(String viejo, String nuevo) {
        if (!lista.containsKey(viejo) || lista.containsKey(nuevo)) {
            return false;
        }
        // armo un mapa nuevo cambiando el nombre viejo por el nuevo
        Map<String, Map<String, Double>> nueva = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Double>> e : lista.entrySet()) {
            String clave = e.getKey();
            if (clave.equals(viejo)) {
                clave = nuevo;
            }
            Map<String, Double> vecinos = new LinkedHashMap<>();
            for (Map.Entry<String, Double> x : e.getValue().entrySet()) {
                String vecino = x.getKey();
                if (vecino.equals(viejo)) {
                    vecino = nuevo;
                }
                vecinos.put(vecino, x.getValue());
            }
            nueva.put(clave, vecinos);
        }
        lista.clear();
        lista.putAll(nueva);
        return true;
    }

    public boolean agregarArista(String origen, String destino, double peso) {
        if (!lista.containsKey(origen) || !lista.containsKey(destino) || origen.equals(destino)) {
            return false;
        }
        if (lista.get(origen).containsKey(destino)) {
            return false; // ya existe
        }
        lista.get(origen).put(destino, peso);
        if (!dirigido) {
            lista.get(destino).put(origen, peso);
        }
        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        if (!lista.containsKey(origen) || !lista.get(origen).containsKey(destino)) {
            return false;
        }
        lista.get(origen).remove(destino);
        if (!dirigido) {
            lista.get(destino).remove(origen);
        }
        return true;
    }

    public boolean modificarArista(String origen, String destino, double nuevoPeso) {
        if (!lista.containsKey(origen) || !lista.get(origen).containsKey(destino)) {
            return false;
        }
        lista.get(origen).put(destino, nuevoPeso);
        if (!dirigido) {
            lista.get(destino).put(origen, nuevoPeso);
        }
        return true;
    }

    public void mostrar() {
        System.out.println("--- Lista de adyacencia ---");
        if (lista.isEmpty()) {
            System.out.println("(vacio)");
            return;
        }
        for (Map.Entry<String, Map<String, Double>> e : lista.entrySet()) {
            String linea = e.getKey() + " -> ";
            for (Map.Entry<String, Double> x : e.getValue().entrySet()) {
                linea = linea + x.getKey() + "(" + x.getValue() + ") ";
            }
            System.out.println(linea.trim());
        }
    }
}
