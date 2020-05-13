package DataStructuresLab;

import DS_Runtimes.TimeTester;
import DataStructuresLab.Exceptions.EmptyListException;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws EmptyListException {
	    // write your code here
        HashMap<String, ArrayList<Long>> dataset = TimeTester.GenerateInsertData();
        //Lets take this data, export it to a txt, then create some graphs in python:
        String[] types = new String[]{"Linked List", "Built In Array", "Binary Search Tree", "Skip List", "Red Black Tree"};
        for(String s : types)
        {
            Utils.WriteToFile.simpleLongFileWrite("C:\\Users\\Jacob Baker\\Desktop\\DataStructuresLab\\Data\\Insert\\" + s + ".txt", dataset.get(s));
        }

        dataset = TimeTester.GenerateDeleteData();
        for(String s : types)
        {
            Utils.WriteToFile.simpleLongFileWrite("C:\\Users\\Jacob Baker\\Desktop\\DataStructuresLab\\Data\\Delete\\" + s + ".txt", dataset.get(s));
        }

        dataset = TimeTester.GenerateSearchData();
        for(String s : types)
        {
            Utils.WriteToFile.simpleLongFileWrite("C:\\Users\\Jacob Baker\\Desktop\\DataStructuresLab\\Data\\Search\\" + s + ".txt", dataset.get(s));
        }

    }
}
