package DataStructuresLab.Testing;

import DataStructuresLab.BinarySearchTree.BinarySearchTree;
import DataStructuresLab.Exceptions.EmptyListException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

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
        BinarySearchTree tree = new BinarySearchTree();
        int[] values = GenerateRandomArray(N);
        for(int n : values)
        {
            tree.Insert(n);
        }
        System.out.println("Dumping...");
        tree.Dump();
    }

    @Test
    void delete() throws EmptyListException {
        BinarySearchTree tree = new BinarySearchTree();
        int[] values = GenerateRandomArray(6);
        for(int n : values)
        {
            tree.Insert(n);
        }

        tree.Dump();
        System.out.println("Deleting : " + values[values.length - 1]);
        boolean t = tree.Delete(values[values.length - 1]);
        tree.Dump();
        boolean f = tree.Delete(-1);

        assertTrue(t);
        assertFalse(f);
    }

    @Test
    void find() throws EmptyListException {
        BinarySearchTree tree = new BinarySearchTree();
        int[] values = GenerateRandomArray(6);
        for(int n : values)
        {
            tree.Insert(n);
        }

        assertTrue(tree.Find(values[values.length-1]));
        assertFalse(tree.Find(-100));
    }

    @Test
    void testToString() {
        BinarySearchTree tree = new BinarySearchTree();
        int[] values = GenerateRandomArray(N);
        for(int n : values)
        {
            tree.Insert(n);
        }

        System.out.println(tree.toString());
    }

    @Test
    void isEmpty() {
        BinarySearchTree tree = new BinarySearchTree();
        assertTrue(tree.isEmpty());
    }

    @Test
    void isSorted() throws EmptyListException {
        BinarySearchTree tree = new BinarySearchTree();
        int[] values = GenerateRandomArray(N);
        for(int n : values)
        {
            tree.Insert(n);
        }

        assertTrue(tree.isSorted());
    }
}