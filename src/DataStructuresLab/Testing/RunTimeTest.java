package DataStructuresLab.Testing;

import DataStructuresLab.*;
import DataStructuresLab.BinarySearchTree.BinarySearchTree;
import DataStructuresLab.BuiltInArrays.BuiltInArray;
import DataStructuresLab.Exceptions.EmptyListException;
import DataStructuresLab.LinkedList.LinkedList;
import DataStructuresLab.RBTree.RBTree;
import DataStructuresLab.SkipList.SkipList;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;

class RunTimeTest {


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

    private HashMap<String, Long> GetRuntimes(int N)
    {
        long start,end, elapsed;
        HashMap<String, Long> runtimes = new HashMap<String, Long>();
        int[] values = GenerateRandomArray(N);

        start = System.nanoTime();
        LinkedList linkedList = new LinkedList(values);
        end = System.nanoTime();
        elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);
        runtimes.put("Linked List", elapsed);

        start = System.nanoTime();
        BuiltInArray BIArray = new BuiltInArray(values);
        end = System.nanoTime();
        elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);
        runtimes.put("Built-In Array", elapsed);

        start = System.nanoTime();
        BinarySearchTree bst = new BinarySearchTree(values);
        end = System.nanoTime();
        elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);
        runtimes.put("Binary Search Tree", elapsed);

        start = System.nanoTime();
        SkipList skipList = new SkipList();
        for(int i : values)
        {
            skipList.Insert(i);
        }
        end = System.nanoTime();
        elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);
        runtimes.put("Skip List", elapsed);

        start = System.nanoTime();
        RBTree rbTree = new RBTree(values);
        end = System.nanoTime();
        elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);
        runtimes.put("Red Black Tree", elapsed);

        return runtimes;
    }

    private void printMap(Map mp)
    {
        Iterator it = mp.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
            it.remove();
        }
    }


    @Test
    void Insertion_10()
    {
        int N = 10;
        HashMap<String, Long> dataSet = GetRuntimes(N);
        System.out.println("N = 10 :");
        printMap(dataSet);
    }

    @Test
    void Insertion_1E3()
    {
        int N = 1000;
        HashMap<String, Long> dataSet = GetRuntimes(N);
        System.out.println("N = 1E3 :");
        printMap(dataSet);
    }

    @Test
    void Insertion_1E4()
    {
        int N = 10000;
        HashMap<String, Long> dataSet = GetRuntimes(N);
        System.out.println("N = 1E4 :");
        printMap(dataSet);
    }

    @Test
    void Insertion_1E5()
    {
        int N = 100000;
        HashMap<String, Long> dataSet = GetRuntimes(N);
        System.out.println("N = 1E5 :");
        printMap(dataSet);
    }

}
