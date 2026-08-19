import java.util.Collections;

class BPlusTree {
    private BPlusTreeNode root;

    // Numero máximo de hijos
    private final int order;

    public BPlusTree(int order) {
        if (order < 3) {
            throw new IllegalArgumentException("Order must be at least 3");
        }

        root = new BPlusTreeNode(true);
        this.order = order;
    }

    // Encontrar el nodo hoja
    private BPlusTreeNode findLeaf(int key) {
        BPlusTreeNode node = root;

        while (!node.isLeaf) {
            int i = 0;

            while (i < node.keys.size()
                    && key >= node.keys.get(i)) {
                i++;
            }

            node = node.children.get(i);
        }

        return node;
    }

    // Insertar una llave
    public void insert(int key) {
        BPlusTreeNode leaf = findLeaf(key);

        insertIntoLeaf(leaf, key);

        // Dividir hoja si supera el maximo
        if (leaf.keys.size() > order - 1) {
            splitLeaf(leaf);
        }
    }

    // Insertar llave en una hoja ordenada
    private void insertIntoLeaf(
            BPlusTreeNode leaf, int key
    ) {
        int pos = Collections.binarySearch(
                leaf.keys, key
        );

        if (pos < 0) {
            pos = -(pos + 1);
        }

        leaf.keys.add(pos, key);
    }

    // Dividir una hoja
    private void splitLeaf(BPlusTreeNode leaf) {
        int mid = (order + 1) / 2;

        BPlusTreeNode newLeaf = new BPlusTreeNode(true);

        // Mover la mitad a una nueva hoja
        newLeaf.keys.addAll(
                leaf.keys.subList(mid, leaf.keys.size()));

        leaf.keys.subList(mid, leaf.keys.size()).clear();

        // Mantener las hojas conectadas
        newLeaf.next = leaf.next;
        leaf.next = newLeaf;

        // Si el root esta dividio escoger nuevo root
        if (leaf == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);

            newRoot.keys.add(newLeaf.keys.get(0));

            newRoot.children.add(leaf);
            newRoot.children.add(newLeaf);

            root = newRoot;
        } else {
            insertIntoParent(leaf, newLeaf, newLeaf.keys.get(0)
            );
        }
    }

    // Insertar un nodo hijo en uno padre
    private void insertIntoParent(
            BPlusTreeNode left,
            BPlusTreeNode right,
            int key
    ) {
        BPlusTreeNode parent = findParent(root, left);

        if (parent == null) {
            throw new RuntimeException(
                    "Parent node not found"
            );
        }

        int pos = Collections.binarySearch(parent.keys, key);

        if (pos < 0) {
            pos = -(pos + 1);
        }

        parent.keys.add(pos, key);
        parent.children.add(pos + 1, right);

        // Dividir el nodo interno
        if (parent.keys.size() > order - 1) {
            splitInternal(parent);
        }
    }

    // Split an internal node
    private void splitInternal(BPlusTreeNode internal) {
        int mid = (order + 1) / 2;

        int promotedKey = internal.keys.get(mid);

        BPlusTreeNode newInternal = new BPlusTreeNode(false);

        // Mover llaves
        newInternal.keys.addAll(
                internal.keys.subList(mid + 1, internal.keys.size()));

        internal.keys.subList(mid, internal.keys.size()).clear();

        // Mover los hijos
        newInternal.children.addAll(internal.children.subList(mid + 1, internal.children.size()));

        internal.children.subList(mid + 1, internal.children.size()).clear();

        // Si se divide el root crear un nuevo root
        if (internal == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);

            newRoot.keys.add(promotedKey);
            newRoot.children.add(internal);
            newRoot.children.add(newInternal);

            root = newRoot;
        } else {
            insertIntoParent(internal, newInternal, promotedKey);
        }
    }

    // Encontrar el padre de un nodo
    private BPlusTreeNode findParent(
            BPlusTreeNode current,
            BPlusTreeNode target
    ) {
        if (current.isLeaf ||
                current.children.isEmpty()) {
            return null;
        }

        for (BPlusTreeNode child : current.children) {

            if (child == target) {
                return current;
            }

            BPlusTreeNode parent =
                    findParent(child, target);

            if (parent != null) {
                return parent;
            }
        }

        return null;
    }

    // Buscar una llave
    public boolean search(int key) {
        BPlusTreeNode leaf = findLeaf(key);

        int pos = Collections.binarySearch(leaf.keys, key);

        return pos >= 0;
    }

    // Imprimir el padre
    public void printTree() {
        printNode(root, 0);
    }

    private void printNode(BPlusTreeNode node, int level
    ) {
        System.out.println("Level " + level + ": " + node.keys);

        if (!node.isLeaf) {
            for (BPlusTreeNode child : node.children) {
                printNode(child, level + 1);
            }
        }
    }
}