// Programa principal del trabajo de grafos
// aca armo un grafo de ejemplo y muestro como se ve en cada representacion

public class Main {

    // le agrego los mismos vertices y aristas a cualquier representacion
    // asi el grafo es el mismo en las 4
    static void armarGrafo(Grafo g) {
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarVertice("D");

        g.agregarArista("A", "B", 2);
        g.agregarArista("A", "C", 5);
        g.agregarArista("B", "C", 1);
        g.agregarArista("C", "D", 3);

        // tambien pruebo unas modificaciones
        g.modificarArista("A", "B", 7);   // le cambio el peso a A-B
        g.modificarVertice("D", "E");     // renombro D a E
        g.eliminarArista("A", "C");       // borro la arista A-C
    }

    public static void main(String[] args) {
        boolean dirigido = false;

        System.out.println("=== Grafo de ejemplo (no dirigido) ===");
        System.out.println("Vertices: A, B, C, D");
        System.out.println("Aristas: A-B(2), A-C(5), B-C(1), C-D(3)");
        System.out.println("Despues: A-B pasa a 7, D se renombra a E, se borra A-C");
        System.out.println("");

        MatrizAdyacencia ma = new MatrizAdyacencia(dirigido);
        armarGrafo(ma);
        ma.mostrar();
        System.out.println("");

        ListaAdyacencia la = new ListaAdyacencia(dirigido);
        armarGrafo(la);
        la.mostrar();
        System.out.println("");

        MatrizIncidencia mi = new MatrizIncidencia(dirigido);
        armarGrafo(mi);
        mi.mostrar();
        System.out.println("");

        ListaIncidencia li = new ListaIncidencia(dirigido);
        armarGrafo(li);
        li.mostrar();
    }
}
