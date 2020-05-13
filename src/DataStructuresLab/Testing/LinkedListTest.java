package DataStructuresLab.Testing;

import DataStructuresLab.Exceptions.EmptyListException;
import DataStructuresLab.LinkedList.LinkedList;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    final int N = 100;

    private LinkedList GenerateList()
    {
        Random rand = new Random();
        int N = 100;
        int[] values = new int[N];
        for(int i = 0; i < values.length; i++)
        {
            values[i] = rand.nextInt(N);
        }
        LinkedList rtn = new LinkedList(values);
        return rtn;
    }

    private int[] GenerateRandomArray(int N)
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
    void testToString() {
        LinkedList list = new LinkedList(new int[]{5,4,3,2,1});
        String A = "1, 2, 3, 4, 5";
        String B = list.toString();
        assertEquals(A,B);
    }

    @Test
    void Insert() throws EmptyListException {
        LinkedList list = new LinkedList();
        Random rand = new Random();
        int N = 100;
        int[] values = new int[N];
        for(int i = 0; i < values.length; i++)
        {
            values[i] = rand.nextInt(N);
        }

        for(int n : values)
        {
            list.Insert(n);
        }
        Arrays.sort(values);
        int[] A = values;
        int[] B = list.toArray();
        assertArrayEquals(A, B);
    }

    @Test
    void delete() throws EmptyListException {
//        LinkedList list = new LinkedList(new int[]{5,4,2,1,3});
//        int[] values = new int[]{1,2,3,5};
//        list.Delete(4);
        int[] values = GenerateRandomArray(5);
        LinkedList list = new LinkedList(values);
//        int[] B = list.toArray();
        for(int n : values)
        {
            list.Delete(n);
        }
        assertTrue(list.isEmpty());
    }

    @Test
    void find() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);

        LinkedList list = new LinkedList(randArray);
        boolean isFound = list.Find(5);
        assertTrue(isFound);
    }

    @Test
    void toArray() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);
        LinkedList list = new LinkedList(randArray);
        Arrays.sort(randArray);
        int[] A = randArray;
        int[] B = list.toArray();
        assertArrayEquals(A,B);
    }

    @Test
    void isSorted() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);
        LinkedList list = new LinkedList(randArray);
        Arrays.sort(randArray);
        int[] A = randArray;
        int[] B = list.toArray();

        assertTrue(list.isSorted());
        assertArrayEquals(A,B);
    }
}