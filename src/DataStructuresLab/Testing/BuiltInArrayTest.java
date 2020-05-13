package DataStructuresLab.Testing;

import DataStructuresLab.BuiltInArrays.BuiltInArray;
import DataStructuresLab.Exceptions.EmptyListException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BuiltInArrayTest {

    private int N = 129;

    private int[] GenerateRandomArray(int N)
    {
        int[] rtn = new int[N];
        Random rand = new Random();

        for(int i = 0; i < rtn.length; i++)
        {
            rtn[i] = rand.nextInt(N);
        }
//        System.out.println(Arrays.toString(rtn));
        return rtn;
    }

    @Test
    void insert() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);
        BuiltInArray list = new BuiltInArray();
        for(int n : randArray)
        {
            list.Insert(n);
        }
        Arrays.sort(randArray);
        for(int i = 0; i < list.Count(); i++)
        {
            int actual = list.get(i);
            int expected = randArray[i];
            assertEquals(expected, actual);
        }

//        System.out.println(list.toString());
    }

    @Test
    void delete() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);
        BuiltInArray list = new BuiltInArray(randArray);
        Arrays.sort(randArray);

        for(int i = 0; i < list.Count(); i++)
        {
            int actual = list.get(i);
            assertEquals(randArray[i], actual);
        }

        //Delete entire list:
        for(int i = 0; i < randArray.length; i++)
        {
            list.Delete(randArray[i]);
        }
//        System.out.println(list.toString());
        assertTrue(list.isEmpty());
    }

    @Test
    void find() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);
        BuiltInArray list = new BuiltInArray(randArray);
        Arrays.sort(randArray);
        for(int i = 0; i < list.Count(); i++)
        {
            int actual = randArray[i];
            int expected = list.get(i);
            assertEquals(actual, expected);
        }

        for(int n : randArray)
        {
            assertTrue(list.Find(n));
        }
    }

    @Test
    void isEmpty() {
        BuiltInArray list = new BuiltInArray();
        assertTrue(list.isEmpty());
    }

    @Test
    void isSorted() throws EmptyListException {
        int[] randArray = GenerateRandomArray(N);
        BuiltInArray list = new BuiltInArray(randArray);
        // Sort our Expected Array
        Arrays.sort(randArray);
        for(int i = 0; i < randArray.length; i++)
        {
            int actual = list.get(i);
            int expected = randArray[i];
            assertEquals(expected, actual);
        }

        assertTrue(list.isSorted());
    }
}