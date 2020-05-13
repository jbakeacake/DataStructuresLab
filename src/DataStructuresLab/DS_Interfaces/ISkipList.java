package DataStructuresLab.DS_Interfaces;

import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.Exceptions.EmptyListException;

public interface ISkipList {
    int Count(); //Returns the amount of items in the structure
    int[] toArray() throws EmptyListException; //Returns this structure as an array
    void Real_Insert(int searchKey, int value); // inserts element into ordered position of data structure
    boolean Delete(int searchKey) throws EmptyListException; // returns true if element is in data structure and removes it
    boolean Find(int value) throws EmptyListException;  // returns true if element is in list
    String toString(); // get list in order
    boolean isEmpty();
    boolean isSorted() throws EmptyListException; // Returns true if the list is in ascending order
}
