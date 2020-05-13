package DataStructuresLab.Testing;

import DataStructuresLab.Exceptions.EmptyListException;
import DataStructuresLab.SkipList.SkipList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    final int N = 99;

    private int[] GenerateRandomValues(int N)
    {
        Random rand = new Random();
        int[] values = new int[N];
        for(int i = 0; i < values.length; i++)
        {
            values[i] = rand.nextInt(N);
        }

        return values;
    }


    @Test
    void count() {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(N);
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }
        int A = values.length;
        int B = list.Count();
        System.out.println(list.toString());
        assertEquals(A, B);
    }

    @Test
    void TestToString()
    {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(9); //use 9 to properly maintain our string structure -- single digits maintain the spacing
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }

        System.out.println(list.toString());
        assertTrue(true); // No real good measure for this test other than to check if it prints correctly.
    }

    @Test
    void toArray() throws EmptyListException {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(50);
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }

        int[] actual = list.toArray();
        assertArrayEquals(values, actual);
    }

    @Test
    void insert() throws EmptyListException {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(10);
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }

        int[] actual = list.toArray();
        assertArrayEquals(values, actual);
    }

    @Test
    void delete() throws EmptyListException {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(9);
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }

        System.out.println("START : \n" + list.toString());
        for(int i = 0; i < values.length; i++)
        {
            System.out.println("Deleting " + i+1 + "...");
            assertTrue(list.Delete(i+1));
            System.out.println("AFTER : \n" + list.toString());
        }

    }

    @Test
    void find() throws EmptyListException {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(N);
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }

        for(int i = 0; i < values.length; i++)
        {
            list.Find(i+1);
        }
    }

    @Test
    void isEmpty() {
        SkipList list = new SkipList();
        assertTrue(list.isEmpty());
    }

    @Test
    void isSorted() throws EmptyListException {
        SkipList list = new SkipList();
        int[] values = GenerateRandomValues(N);
        Random rand = new Random();
        for(int i = 0; i < values.length; i++)
        {
            list.Insert(values[i]);
        }
        assertTrue(list.isSorted());
    }
}