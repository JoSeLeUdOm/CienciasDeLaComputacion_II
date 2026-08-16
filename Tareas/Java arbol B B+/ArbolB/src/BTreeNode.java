public class BTreeNode {
    int[] keys; //Llaves del arbol
    int t; //Grado minimo
    BTreeNode[] children;
    int n; //Numero actual de llaves
    boolean leaf; //true cuando el nodo es una hoja
    public BTreeNode(int t, boolean leaf) {
        this.t=t;
        this.leaf=leaf;

        keys = new int[2*t-1];
        children = new BTreeNode[2*t];
        n=0;

    }

    //Funcion de busqueda
    public BTreeNode search(int key){
        int i =0;
        while (i<=n && key>keys[i]){
            i++;
        }
        if(key==keys[i]){
            return this;
        }
        if (leaf){
            return null;
        }
        return children[i].search(key);
    }

    //funcion de insersion
    public void insert(int key){
        int i = n-1;
        if (leaf){
            while(i>=0 && keys[i]>key){
                keys[i+1]=keys[i];
                i--;
            }
            keys[i+1]=key;
            n++;
        }else{
            while(i>=0 && keys[i]>key){
                i--;
            }
            i++;
            if (children[i].n==t*2-1){
                splitChild(i,children[i]);
                if(key>keys[i]){
                    i++;
                }
            }
            children[i].insert(key);
        }
    }

    public void splitChild(int i,BTreeNode node){

    }

}
