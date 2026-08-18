public class BTree {
    //Apuntador a la raiz
    private BTreeNode root;

    //Grado Minimo
    private int t;

    public BTree(int t) {
        this.t = t;
        root = null;
    }

    //Buscar una llave en el arbol
    public BTreeNode search(int key) {
        return (root == null) ? null : root.search(key);
    }

    //Insertar una llave
    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode newRoot = new BTreeNode(t, false);
                newRoot.children[0] = root;
                newRoot.splitChild(0, root);

                int i = 0;

                if (newRoot.keys[0] < key)
                    i++;

                newRoot.children[i].insert(key);
                root = newRoot;
            } else {
                root.insert(key);
            }
        }
    }

    //Imprimir el arbol
    public void printBTree() {
        if (root != null)
            root.printInOrder();

        System.out.println();
    }
}
