package DataStructuresLab.BinarySearchTree;

import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.Exceptions.EmptyListException;

import java.util.ArrayList;
import java.util.Arrays;

public class BinarySearchTree implements IBasicDataStructure {

    private int Count;
    public BST_Node root;

    public BinarySearchTree()
    {
        this.Count = 0;
        this.root = null;
    }

    public BinarySearchTree(int[] toInsert)
    {
        this.Count = 0;
        this.root = null;
        for(int n : toInsert)
        {
            this.Insert(n);
        }
    }

    @Override
    public void Insert(int value)
    {
        this.root = Recursive_Insert(this.root, value);
    }

    private BST_Node Recursive_Insert(BST_Node root, int value) {
        if(root == null)
        {
            this.Count++;
            return new BST_Node(value);
        }

        if(value >= root.value)
        {
            root.right = Recursive_Insert(root.right, value);
        }
        else
        {
            root.left = Recursive_Insert(root.left, value);
        }

        return root;
    }

    /*
    minValueNode(min:BST_Node) -> BST_Node

    This method finds the the minimum node in the right subtree of min's parent. I.e. min should be the right child of its parent.
     */
    private BST_Node minValueNode(BST_Node min)
    {
        while(min.left != null)
        {
            min = min.left;
        }
        return min;
    }

    @Override
    public boolean Delete(int value) throws EmptyListException {
        int prevCount = this.Count;
        this.root = Recursive_Delete(this.root, value);
        return (prevCount == this.Count) ? false : true;
    }

    private BST_Node Recursive_Delete(BST_Node root, int value) throws EmptyListException {
        if(root == null)
        {
            return null;
        }

        if(value < root.value) //If our value is less than / equal to our root
        {
            root.left = Recursive_Delete(root.left, value);
        }
        else if(value > root.value)
        {
            root.right = Recursive_Delete(root.right, value);
        }
        else if(value == root.value)
        {
            /*
            We have 3 different cases that we might hit:
                > If the deletion node has no left child, then propagate our right subtree up
                > If the deletion node has no right child, then propagate our left subtree up
                > If the deletion node has two children, then find the minimum node in the right subtree
             */
            if(root.left == null) //if no left child, propagate right subtree
            {
                this.Count--;
                BST_Node tmp = root.right;
                root = null;
                return tmp;
            }
            else if(root.right == null) //if no right child, propagate left subtree
            {
                this.Count--;
                BST_Node tmp = root.left;
                root = null;
                return tmp;
            }
            //if we have two children, find min node in right subtree, and copy value into deletion node.
            root.value = minValueNode(root.right).value;
            //Delete our replacement node:
            root.right = Recursive_Delete(root.right, root.value);
            this.Count--;
        }

        return root;
    }

    @Override
    public boolean Find(int value) throws EmptyListException
    {
        if(isEmpty()) throw new EmptyListException();
        boolean[] res = new boolean[1];
        res[0] = false; // we need a value that can persist over several calls
        Recursive_Find(this.root, value, res);

        return res[0];
    }

    private void Recursive_Find(BST_Node root, int value, boolean[] res) throws EmptyListException {

        if(root == null) return;

        if(value > root.value)
        {
            Recursive_Find(root.right, value, res);
        }
        else if(value < root.value)
        {
            Recursive_Find(root.left, value, res);
        }
        else if(value == root.value)
        {
            res[0] = true;
            return;
        }

        return;
    }

    public void Dump()
    {
        DumpTree(this.root, 0);
    }

    private void DumpTree(BST_Node root, int counter)
    {
        if(root == null) return;
        String toPrint = "";
        for(int i = 0; i < counter; i++)
        {
            toPrint += "_";
        }
        System.out.println(toPrint + root.value);
        counter++;
        DumpTree(root.left, counter);
        DumpTree(root.right, counter);
    }

    @Override
    public String toString()
    {
        String[] toPrint = new String[1];
        toPrint[0] = ""; //We need to persist over several different calls, so lets use an array
        Recursive_toString(this.root, toPrint);
        return toPrint[0];
    }

    private void Recursive_toString(BST_Node root, String[] toPrint) {
        if(root == null) return;
        //Let's do an inorder traversal:
        Recursive_toString(root.left, toPrint);
        toPrint[0] = toPrint[0].concat(Integer.toString(root.value));
        Recursive_toString(root.right, toPrint);
    }

    @Override
    public boolean isEmpty() {
        if(this.Count == 0 && this.root == null) return true;
        return false;
    }

    @Override
    public boolean isSorted() throws EmptyListException {
        ArrayList<Integer> res = new ArrayList<>();
        Recursive_IsSorted(this.root, res);
        //We can determine if our BST is sorted by inputting values from an in order traversal of our BST into an array, and then comparing that array
        //to it's sorted version.
        int[] actual = new int[this.Count];
        for(int i = 0; i < actual.length; i++)
        {
            actual[i] = res.get(i);
        }
        //Make a copy of our actual array, and sort it -- if they're equal, they must be true.
        int[] expected = new int[this.Count];
        System.arraycopy(actual,0,expected,0,actual.length);
        Arrays.sort(expected);
        return (Arrays.equals(actual,expected));
    }

    private void Recursive_IsSorted(BST_Node root, ArrayList<Integer> res)
    {
        if(root == null) return;

        //Do an inorder traversal
        Recursive_IsSorted(root.left, res);
        System.out.print(root.value + " ");
        res.add(Integer.valueOf(root.value));
        Recursive_IsSorted(root.right, res);
    }

}
