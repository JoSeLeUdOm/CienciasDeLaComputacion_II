// Main class
public class Main {
    public static void main(String[] args) {

        BPlusTree tree = new BPlusTree(3);

        //Insertar claves
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(60);

        System.out.println("Árbol despues de inseciÓn:");

        tree.printTree();

        System.out.println("Buscar para 30: " + tree.search(30));

        System.out.println("Buscar para 25: " + tree.search(25));
    }
}
