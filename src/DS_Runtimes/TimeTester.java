package DS_Runtimes;

import DataStructuresLab.BinarySearchTree.BinarySearchTree;
import DataStructuresLab.BuiltInArrays.BuiltInArray;
import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.Exceptions.EmptyListException;
import DataStructuresLab.LinkedList.LinkedList;
import DataStructuresLab.RBTree.RBTree;
import DataStructuresLab.SkipList.SkipList;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TimeTester {

    private static int[] GenerateRandomArray(int N)
    {
        Random rand = new Random();
        int[] rtn = new int[N];
        for(int i = 0; i < rtn.length; i++)
        {
            rtn[i] = rand.nextInt(N);
        }

        return rtn;
    }

    private static HashMap<String, Long> CompileInsertRuntimes(int N)
    {
        long start,end, elapsed;
        HashMap<String, Long> runtimes = new HashMap<String, Long>();
        int[] values = GenerateRandomArray(N);

        LinkedList linkedList = new LinkedList();
        elapsed = GetInsertRuntime(linkedList, values);
        runtimes.put("Linked List", elapsed);

        BuiltInArray BIArray = new BuiltInArray();
        elapsed = GetInsertRuntime(BIArray, values);
        runtimes.put("Built In Array", elapsed);

        BinarySearchTree bst = new BinarySearchTree();
        elapsed = GetInsertRuntime(bst, values);
        runtimes.put("Binary Search Tree", elapsed);

        SkipList skipList = new SkipList();
        elapsed = GetInsertRuntime(skipList, values);
        runtimes.put("Skip List", elapsed);

        RBTree rbTree = new RBTree();
        elapsed = GetInsertRuntime(rbTree, values);
        runtimes.put("Red Black Tree", elapsed);

        return runtimes;
    }

    private static long GetInsertRuntime(IBasicDataStructure datastruct, int[] values)
    {
        long start,end;
        start = System.nanoTime();
        for(int n : values)
        {
            datastruct.Insert(n);
        }
        end = System.nanoTime();
        return (end - start);
    }

    private static HashMap<String, Long> CompileDeleteRuntimes(int N) throws EmptyListException {
        long start,end, elapsed;
        HashMap<String, Long> runtimes = new HashMap<String, Long>();
        int[] values = GenerateRandomArray(N);

        LinkedList linkedList = new LinkedList();
        elapsed = GetDeleteRuntime(linkedList, values);
        runtimes.put("Linked List", elapsed);

        BuiltInArray BIArray = new BuiltInArray();
        elapsed = GetDeleteRuntime(BIArray, values);
        runtimes.put("Built In Array", elapsed);

        BinarySearchTree bst = new BinarySearchTree();
        elapsed = GetDeleteRuntime(bst, values);
        runtimes.put("Binary Search Tree", elapsed);

        SkipList skipList = new SkipList();
        elapsed = GetDeleteRuntime(skipList, values);
        runtimes.put("Skip List", elapsed);

        RBTree rbTree = new RBTree();
        elapsed = GetDeleteRuntime(rbTree, values);
        runtimes.put("Red Black Tree", elapsed);

        return runtimes;
    }

    private static long GetDeleteRuntime(IBasicDataStructure datastruct, int[] values) throws EmptyListException {
        long start,end;

        //Insert values to prep for deletion:
        for(int n : values)
        {
            datastruct.Insert(n);
        }

        start = System.nanoTime();
        if(values.length != 0)
        {
            for(int n : values)
            {
                datastruct.Delete(n);
            }
        }
        end = System.nanoTime();
        return (end - start);
    }

    private static HashMap<String, Long> CompileSearchRuntimes(int N) throws EmptyListException {
        long start,end, elapsed;
        HashMap<String, Long> runtimes = new HashMap<String, Long>();
        int[] values = GenerateRandomArray(N);

        LinkedList linkedList = new LinkedList();
        elapsed = GetSearchRuntime(linkedList, values);
        runtimes.put("Linked List", elapsed);

        BuiltInArray BIArray = new BuiltInArray();
        elapsed = GetSearchRuntime(BIArray, values);
        runtimes.put("Built In Array", elapsed);

        BinarySearchTree bst = new BinarySearchTree();
        elapsed = GetSearchRuntime(bst, values);
        runtimes.put("Binary Search Tree", elapsed);

        SkipList skipList = new SkipList();
        //Finding values in skip list is different, you need a key -- so in this experiment lets assume we know all the keys,
        //the catch being, they're in a randomized order. We randomize the keys array to be more in line with our previous experiment.
        //We start our keys at 1, and go to N, the length of the array of values we inserted:
        //Insertion:
        for(int n : values)
        {
            skipList.Insert(n);
        }
        List<Integer> keys = new ArrayList<Integer>(N+1);
        for(int i = 0; i < keys.size(); i++)
        {
            keys.add(i+1); //We start key'ing at 1
        }
        //Shuffle the key array:
        Collections.shuffle(keys);
        //Place into primitive array to use in GetSearchRuntime(...)
        int[] basicKeys = new int[N+1];
        for(int i = 0; i < keys.size(); i++)
        {
            basicKeys[i] = keys.get(i);
        }
        elapsed = GetSearchRuntime(skipList, basicKeys);
        runtimes.put("Skip List", elapsed);

        RBTree rbTree = new RBTree();
        elapsed = GetSearchRuntime(rbTree, values);
        runtimes.put("Red Black Tree", elapsed);

        return runtimes;
    }

    private static long GetSearchRuntime(IBasicDataStructure datastruct, int[] values) throws EmptyListException {
        long start,end;

        start = System.nanoTime();
        for(int n : values)
        {
            datastruct.Find(n);
        }
        end = System.nanoTime();
        return (end - start);
    }

    private static void printMap(Map mp)
    {
        Iterator it = mp.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
            it.remove();
        }
    }

    public static HashMap<String, ArrayList<Long>> GenerateInsertData()
    {
        int N = (int) Math.pow(10.0,4.0);
        System.out.println(N);
        HashMap<String, ArrayList<Long>> dataframe = new HashMap<String,ArrayList<Long>>();
        ArrayList<Long> linkedList = new ArrayList<>(), biArray = new ArrayList<>(), bst = new ArrayList<>(), skipList = new ArrayList<>(), rbt = new ArrayList<>();

        for(int i = 0; i <= N; i+=50)
        {
            System.out.println("Generating Data for " + i + "...");

            HashMap<String, Long> dataSet = CompileInsertRuntimes(i);
            linkedList.add(dataSet.get("Linked List"));
            biArray.add(dataSet.get("Built In Array"));
            bst.add(dataSet.get("Binary Search Tree"));
            skipList.add(dataSet.get("Skip List"));
            rbt.add(dataSet.get("Red Black Tree"));
        }

        dataframe.put("Linked List", linkedList);
        dataframe.put("Built In Array", biArray);
        dataframe.put("Binary Search Tree", bst);
        dataframe.put("Skip List", skipList);
        dataframe.put("Red Black Tree", rbt);

        return dataframe;
    }

    public static HashMap<String, ArrayList<Long>> GenerateDeleteData() throws EmptyListException {
        int N = (int) Math.pow(10.0,4.0);
        System.out.println(N);
        HashMap<String, ArrayList<Long>> dataframe = new HashMap<String,ArrayList<Long>>();
        ArrayList<Long> linkedList = new ArrayList<>(), biArray = new ArrayList<>(), bst = new ArrayList<>(), skipList = new ArrayList<>(), rbt = new ArrayList<>();

        for(int i = 0; i <= N; i+=50)
        {
            System.out.println("Generating Data for " + i + "...");

            HashMap<String, Long> dataSet = CompileDeleteRuntimes(i);
            linkedList.add(dataSet.get("Linked List"));
            biArray.add(dataSet.get("Built In Array"));
            bst.add(dataSet.get("Binary Search Tree"));
            skipList.add(dataSet.get("Skip List"));
            rbt.add(dataSet.get("Red Black Tree"));
        }

        dataframe.put("Linked List", linkedList);
        dataframe.put("Built In Array", biArray);
        dataframe.put("Binary Search Tree", bst);
        dataframe.put("Skip List", skipList);
        dataframe.put("Red Black Tree", rbt);

        return dataframe;
    }

    public static HashMap<String, ArrayList<Long>> GenerateSearchData() throws EmptyListException {
        int N = (int) Math.pow(10.0,4.0);
        System.out.println(N);
        HashMap<String, ArrayList<Long>> dataframe = new HashMap<String,ArrayList<Long>>();
        ArrayList<Long> linkedList = new ArrayList<>(), biArray = new ArrayList<>(), bst = new ArrayList<>(), skipList = new ArrayList<>(), rbt = new ArrayList<>();

        for(int i = 0; i <= N; i+=50)
        {
            System.out.println("Generating Data for " + i + "...");

            HashMap<String, Long> dataSet = CompileDeleteRuntimes(i);
            linkedList.add(dataSet.get("Linked List"));
            biArray.add(dataSet.get("Built In Array"));
            bst.add(dataSet.get("Binary Search Tree"));
            skipList.add(dataSet.get("Skip List"));
            rbt.add(dataSet.get("Red Black Tree"));
        }

        dataframe.put("Linked List", linkedList);
        dataframe.put("Built In Array", biArray);
        dataframe.put("Binary Search Tree", bst);
        dataframe.put("Skip List", skipList);
        dataframe.put("Red Black Tree", rbt);

        return dataframe;
    }
}
