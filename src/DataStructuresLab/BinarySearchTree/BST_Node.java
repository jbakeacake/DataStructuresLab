package DataStructuresLab.BinarySearchTree;

public class BST_Node {

    public int value;
    public BST_Node left, right, parent;

    public BST_Node(int value)
    {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public BST_Node() //Default class for our root
    {
        this.left = null;
        this.right = null;
    }
}
