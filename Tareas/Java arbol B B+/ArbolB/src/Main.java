public class Main {
    static void main(String[] args) {
        BTree bTree = new BTree(3);
        bTree.insert(10);
        bTree.insert(20);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(12);
        bTree.insert(30);

        System.out.print("B-tree : ");
        bTree.printBTree();

        int searchKey = 6;
        BTreeNode foundNode = bTree.search(searchKey);

        if (foundNode != null)
            System.out.println("Key " + searchKey + " found in the B-tree.");
        else
            System.out.println("Key " + searchKey + " not found in the B-tree.");
    }
}

