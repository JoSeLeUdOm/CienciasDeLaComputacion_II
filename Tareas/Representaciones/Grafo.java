// interfaz con las operaciones que tienen que tener las 4 representaciones
public interface Grafo {

    boolean agregarVertice(String v);
    boolean eliminarVertice(String v);
    boolean modificarVertice(String viejo, String nuevo);

    boolean agregarArista(String origen, String destino, double peso);
    boolean eliminarArista(String origen, String destino);
    boolean modificarArista(String origen, String destino, double nuevoPeso);

    void mostrar();
}
