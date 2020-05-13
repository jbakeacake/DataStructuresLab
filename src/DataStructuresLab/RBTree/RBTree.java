package DataStructuresLab.RBTree;

import DataStructuresLab.BinarySearchTree.BST_Node;
import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.Exceptions.EmptyListException;

import java.awt.*;
import java.util.Arrays;

public class RBTree implements IBasicDataStructure {

    public RBNode root, sentinel;

    public RBTree()
    {
        this.sentinel = new RBNode();
        this.root = this.sentinel;
    }

    public RBTree(int[] toInsert)
    {
        this.sentinel = new RBNode();
        this.root = this.sentinel;
        for(int n : toInsert)
        {
            this.Insert(n);
        }
    }

    /*
    Insert(value : int) -> void

    Recursive kickoff function for RB_Insert(...)

    value : int >> the value to be inserted into new RBNode
     */
    @Override
    public void Insert(int value) {
        RBNode toInsert = new RBNode(value, this.sentinel);
        RB_Insert(this.root, toInsert);
    }

    private void RB_Insert(RBNode T, RBNode z)
    {
        RBNode x,y;
        x = this.root; // Start at our root
        y = this.sentinel; // Set
        while(x != this.sentinel) // Search for our insertion point
        {
            y = x; // Set y to the last available node before we hit our null leaf nodes
            if(z.key < x.key)
            {
                x = x.left;
            }
            else
            {
                x = x.right;
            }
        }
        z.parent = y; // set our insertion node's parent to the inorder predecessor
        if(y == this.sentinel) // if the inorder predecessor is our sentinel (T.Nil) we must be inserting at the root
        {
            this.root = z; // set a new root
        }
        else if(z.key < y.key) // if our insertion node is less than our parent, set it to the left of our parent
        {
            y.left = z;
        }
        else //otherwise set it to the right
        {
            y.right = z;
        }

        z.left = this.sentinel; //init to our sentinel
        z.right = this.sentinel;
        z.color = Color.RED; //color insertion node to red
        RB_Insert_Fixup(T,z); //Call a fix-up in case we need to re-color
    }

    private void RB_Insert_Fixup(RBNode T, RBNode z)
    {
        RBNode y;
        while(z.parent.color == Color.RED) // if our parents node is red, we're violating RB properties
        {
            if(z.parent == z.parent.parent.left) //if our current node's parent is the left child of our insertion node's grandparent
            {
               y = z.parent.parent.right; // get right child of our current nodes grandparent
               if(y.color == Color.RED) //if it's red, recolor to maintain properties, and to ensure we maintain black height
               {
                   //Make the grandparent children black
                   z.parent.color = Color.BLACK;
                   y.color = Color.BLACK;
                   //Make the grandparent red
                   z.parent.parent.color = Color.RED;
                   //Set z to our grandparent so we can check the rest of our RB Tree
                   z = z.parent.parent;
               }
               else // if the right child of our current node is black
               {
                   if(z == z.parent.right) // If z is the right child
                   {
                       z = z.parent; //set z to our parent to prepare for a rotate
                       LeftRotate(T, z); //Left rotate to get our child nodes in the correct position
                   }
                   // Maintain our black height property
                   z.parent.color = Color.BLACK;
                   z.parent.parent.color = Color.RED;
                   // Right rotate to balance our tree
                   RightRotate(T,z.parent.parent);
               }
            }
            else// if z's parent is not the right child of z's grandparent, then we must be the left
            {
                //Do the same operations as we would do on the right, reversing each direction (left/right) as we go.
                y = z.parent.parent.left;
                if(y.color == Color.RED)
                {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                }
                else
                {
                    if(z == z.parent.left)
                    {
                        z = z.parent;
                        RightRotate(T, z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    LeftRotate(T,z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK; // Just to make sure we maintain RB Tree props, recolor root to black
    }

    /*
    Delete(value : int) -> boolean

    Recursive kickoff function for our RB_Delete(...). Finds the node of deletion then uses it as an
    argument in our recursive delete method.

    value : int >> the key of the node to be deleted

    Return : boolean >> if deleted -> true | else false
     */
    @Override
    public boolean Delete(int value) throws EmptyListException {
        RBNode toDelete = Find_Node(value);
        RB_Delete(this.root, toDelete);
        return true;
    }

    private void RB_Delete(RBNode T, RBNode z)
    {
        RBNode x,y;
        y = z;
        Color original = y.color;
        if(z.left == this.sentinel)
        {
            x = z.right;
            RB_Transplant(T,z,z.right);
        }
        else if(z.right == this.sentinel)
        {
            x = z.left;
            RB_Transplant(T,z,z.left);
        }
        else
        {
            y = Tree_Min(z.right); // in order successor
            original = y.color; // color of successor
            x = y.right; //right child of in order successor
            if(y.parent == z)
            {
                x.parent = y;
            }
            else
            {
                RB_Transplant(T, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            RB_Transplant(T,z,y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if(original == Color.BLACK)
        {
            RB_Delete_Fixup(T,x);
        }
    }

    private void RB_Delete_Fixup(RBNode T, RBNode x)
    {
        while(x != this.root && x.color == Color.BLACK)
        {
            if(x == x.parent.left)
            {
                RBNode w = x.parent.right;
                if(w.color == Color.RED)
                {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    LeftRotate(T,x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == Color.BLACK && w.right.color == Color.BLACK)
                {
                    w.color = Color.RED;
                    x = x.parent;
                }
                else
                {
                    if(w.right.color == Color.BLACK)
                    {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        RightRotate(T, w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    LeftRotate(T,x.parent);
                    x = this.root;
                }
            }
            else
            {
                RBNode w = x.parent.left;
                if(w.color == Color.RED)
                {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    RightRotate(T,x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == Color.BLACK && w.left.color == Color.BLACK)
                {
                    w.color = Color.RED;
                    x = x.parent;
                }
                else
                {
                    if(w.left.color == Color.BLACK)
                    {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        LeftRotate(T, w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    RightRotate(T,x.parent);
                    x = this.root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private void RB_Transplant(RBNode T, RBNode u, RBNode v)
    {
        if(u.parent == this.sentinel)
        {
            this.root = v;
        }
        else if(u == u.parent.left)
        {
            u.parent.left = v;
        }
        else
        {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private RBNode Tree_Min(RBNode x)
    {
        while(x.left != this.sentinel)
        {
            x = x.left;
        }

        return x;
    }

    private void LeftRotate(RBNode T , RBNode x)
    {
        RBNode y = x.right;
        x.right = y.left;
        if(y.left != this.sentinel)
        {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == this.sentinel)
        {
            this.root = y;
        }
        else if(x == x.parent.left)
        {
            x.parent.left = y;
        }
        else
        {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void RightRotate(RBNode T, RBNode x)
    {
        RBNode y = x.left;
        x.left = y.right;
        if(y.right != this.sentinel)
        {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == this.sentinel)
        {
            root = y;
        }
        else if(x == x.parent.right)
        {
            x.parent.right = y;
        }
        else
        {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    @Override
    public boolean Find(int value) throws EmptyListException {
        boolean[] res = new boolean[1];
        res[0] = false;
        Recursive_Find(this.root, value, res);
        return res[0];
    }

    public RBNode Find_Node(int value)
    {
        return Recursive_Find_Node(this.root, value);
    }

    private void Recursive_Find(RBNode T, int key, boolean[] res)
    {
        if(T == this.sentinel || T == null) return;

        if(key < T.key)
        {
            Recursive_Find(T.left, key, res);
        }
        else if(key > T.key)
        {
            Recursive_Find(T.right, key, res);
        }
        else if(key == T.key)
        {
            res[0] = true;
            return;
        }

        return;
    }

    private RBNode Recursive_Find_Node(RBNode T, int key)
    {
        if(T == this.sentinel || T == null) return null;

        if(key < T.key)
        {
            return Recursive_Find_Node(T.left, key);
        }
        else if(key > T.key)
        {
            return Recursive_Find_Node(T.right, key);
        }
        else if(key == T.key)
        {
            return T;
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return (this.root == this.sentinel || this.root == null) ? true : false;
    }

    @Override
    public boolean isSorted() throws EmptyListException {
        String inorder = this.toString();
        String[] splitInOrder = inorder.split(", ");

        int[] nums = new int[splitInOrder.length];
        for(int i = 0; i < nums.length; i++)
        {
            nums[i] = Integer.parseInt(splitInOrder[i]);
        }

        System.out.println(Arrays.toString(nums));

        int prev, curr;

        for(int i = 1; i < nums.length; i++)
        {
            prev = nums[i-1];
            curr = nums[i];
            if(prev > curr)
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString()
    {
        String[] toPrint = new String[1];
        toPrint[0] = ""; //We need to persist over several different calls, so lets use an array
        Recursive_toString(this.root, toPrint);
        return toPrint[0].substring(0, toPrint[0].length()-2); // substring to the length-2 to remove an unneeded comma and space
    }

    /*
    Recursive_toString(root : RBNode, toPrint : String[])

    In order traversal that concatenates as we traverse through the tree.

     */
    private void Recursive_toString(RBNode root, String[] toPrint) {
        if(root == null || root == this.sentinel) return;
        //Let's do an inorder traversal:
        Recursive_toString(root.left, toPrint);
        toPrint[0] = toPrint[0].concat(Integer.toString(root.key) + ", ");
        Recursive_toString(root.right, toPrint);
    }

    /*
    Dump() -> void

    Calls our recursive DumpTree(...) method, and prints out our RB Tree from top to bottom.
    The print formatting is as follows:
    17 (root)
    _1 (root's left child)
    __0(1's left child)
    __2(1's right child)
    _19(root's right child)

    If only one child is shown for a node, assume that its position is in order according to the parent node value.

     */
    public void Dump()
    {
        DumpTree(this.root, 0);
    }

    /*
    DumpTree(root : RBNode, counter : int) -> void

    Recursive printing function that utilizes a preordered traversal (top to bottom) to print out the keys in our tree.

    root : RBNode >> the current root of the subtree we're on
    counter : int >> the level of the current root

     */
    private void DumpTree(RBNode root, int counter)
    {
        if(root == null || root == this.sentinel) return;
        String toPrint = "";
        for(int i = 0; i < counter; i++)
        {
            toPrint += "_";
        }

        String color = "";
        if(root.color.getRed() == 0)
        {
            color = "Black";
        }
        else
        {
            color = "Red";
        }

        System.out.println(toPrint + root.key + " : " + color);
        counter++;
        DumpTree(root.left, counter);
        DumpTree(root.right, counter);
    }
}
