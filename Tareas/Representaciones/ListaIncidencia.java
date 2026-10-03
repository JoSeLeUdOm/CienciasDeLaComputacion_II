import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Lista de incidencia
// cada vertice guarda una lista con las aristas que inciden en el
public class ListaIncidencia implements Grafo {

    private boolean dirigido;
    private Map<String, List<Arista>> incidencia = new LinkedHashMap<>();
    private List<Arista> aristas = new ArrayList<>();
    private int contador = 1;

    public ListaIncidencia(boolean dirigido) {
        this.dirigido = dirigido;
    }

    private Arista buscarArista(String o, String d) {
        for (int i = 0; i < aristas.size(); i++) {
            if (aristas.get(i).conecta(o, d, dirigido)) {
                return aristas.get(i);
            }
        }
        return null;
    }

    private void quitar(Arista a) {
        aristas.remove(a);
        incidencia.get(a.origen).remove(a);
        incidencia.get(a.destino).remove(a);
    }

    public boolean agregarVertice(String v) {
        if (incidencia.containsKey(v)) {
            return false;
        }
        incidencia.put(v, new ArrayList<>());
        return true;
    }

    public boolean eliminarVertice(String v) {
        if (!incidencia.containsKey(v)) {
            return false;
        }
        // copio la lista para no romper el recorrido al borrar
        List<Arista> copia = new ArrayList<>(incidencia.get(v));
        for (int i = 0; i < copia.size(); i++) {
            quitar(copia.get(i));
        }
        incidencia.remove(v);
        return true;
    }

    public boolean modificarVertice(String viejo, String nuevo) {
        if (!incidencia.containsKey(viejo) || incidencia.containsKey(nuevo)) {
            return false;
        }
        for (int i = 0; i < aristas.size(); i++) {
            Arista a = aristas.get(i);
            if (a.origen.equals(viejo)) {
                a.origen = nuevo;
            }
            if (a.destino.equals(viejo)) {
                a.destino = nuevo;
            }
        }
        // vuelvo a armar el mapa con el nombre cambiado
        Map<String, List<Arista>> nueva = new LinkedHashMap<>();
        for (Map.Entry<String, List<Arista>> e : incidencia.entrySet()) {
            String clave = e.getKey();
            if (clave.equals(viejo)) {
                clave = nuevo;
            }
            nueva.put(clave, e.getValue());
        }
        incidencia.clear();
        incidencia.putAll(nueva);
        return true;
    }

    public boolean agregarArista(String origen, String destino, double peso) {
        if (!incidencia.containsKey(origen) || !incidencia.containsKey(destino) || origen.equals(destino)) {
            return false;
        }
        if (buscarArista(origen, destino) != null) {
            return false;
        }
        Arista a = new Arista("e" + contador, origen, destino, peso);
        contador = contador + 1;
        aristas.add(a);
        incidencia.get(origen).add(a);
        incidencia.get(destino).add(a);
        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        Arista a = buscarArista(origen, destino);
        if (a == null) {
            return false;
        }
        quitar(a);
        return true;
    }

    public boolean modificarArista(String origen, String destino, double nuevoPeso) {
        Arista a = buscarArista(origen, destino);
        if (a == null) {
            return false;
        }
        a.peso = nuevoPeso;
        return true;
    }

    public void mostrar() {
        System.out.println("--- Lista de incidencia ---");
        if (incidencia.isEmpty()) {
            System.out.println("(vacio)");
            return;
        }
        for (Map.Entry<String, List<Arista>> e : incidencia.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}
