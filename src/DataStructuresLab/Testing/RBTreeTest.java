package DataStructuresLab.Testing;

import DataStructuresLab.Exceptions.EmptyListException;
import DataStructuresLab.RBTree.RBNode;
import DataStructuresLab.RBTree.RBTree;
import org.junit.jupiter.api.Test;

import java.security.CodeSigner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeTest {

    final int N = 10;

    private int[] GenerateRandomArray(int N)
    {
        Random rand = new Random();
        int[] rtn = new int[N];
        for(int i = 0; i < rtn.length; i++)
        {
            rtn[i] = rand.nextInt(N);
        }

        return rtn;
    }

    @Test
    void insert() {
//        int[] values = GenerateRandomArray(N);
        int[] values = new int[]{8,18,5,15,17,25,40,80};
        RBTree tree = new RBTree(values);
        System.out.println(Arrays.toString(values));
        // (Honest best way to test this -- other than building a dump method that structures trees nicely -- is to look at the debugger)
        //
        //The first child below a parent represents the left node, and the next child on that same level represents the right node
        // if there is no sibling to the child node, assume correct ordering (see node 9 in example below)
        /*
        e.g.
        8 - parent
        _1 - left node
        __0 (left child of 1)
        __2 (right child of 1)
        _9 - right node
        __29 (right child of 9)
         */
        tree.Dump();

    }

    @Test
    void delete() throws EmptyListException {
        //        int[] values = GenerateRandomArray(N);
        int[] values = new int[]{8,18,5,15,17,25,40,80};
        RBTree tree = new RBTree(values);
        System.out.println(Arrays.toString(values));
        tree.Dump();
        for(int n : values)
        {
            System.out.println("Deleting " + n + "...");
            tree.Delete(n);
            tree.Dump();
        }

        assertTrue(tree.isEmpty());
    }

    @Test
    void find() throws EmptyListException {
        int[] values = GenerateRandomArray(N);
        RBTree tree = new RBTree(values);
        for(int n : values)
        {
            assertTrue(tree.Find(n));
        }
    }

    @Test
    void find_node()
    {
        int[] values = new int[]{8,18,5,15,17,25,40,80};
        RBTree tree = new RBTree(values);
        for(int n : values)
        {
            int key = tree.Find_Node(n).key;
            System.out.println(key);
        }

        RBNode root = tree.root;
        RBNode found = tree.Find_Node(17);
        assertEquals(root, found);
    }
    @Test
    void isEmpty() {
        RBTree tree= new RBTree();
        assertTrue(tree.isEmpty());
    }

    @Test
    void isSorted() throws EmptyListException {
        int[] values = GenerateRandomArray(100);
        RBTree tree = new RBTree(values);
        assertTrue(tree.isSorted());
    }

    @Test
    void testToString() {
        int[] values = GenerateRandomArray(100);
        RBTree tree = new RBTree(values);
        Arrays.sort(values);
        String actual = "[" + tree.toString() + "]";
        assertTrue(actual.compareTo(Arrays.toString(values)) == 0);
    }
}