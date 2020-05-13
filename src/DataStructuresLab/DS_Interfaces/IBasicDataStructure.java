package DataStructuresLab.DS_Interfaces;

import DataStructuresLab.Exceptions.EmptyListException;

public interface IBasicDataStructure {
    void Insert(int value); // inserts element into ordered position of data structure
    boolean Delete(int value) throws EmptyListException; // returns true if element is in data structure and removes it
    boolean Find(int value) throws EmptyListException;  // returns true if element is in list
    String toString(); // get list in order
    boolean isEmpty();
    boolean isSorted() throws EmptyListException; // Returns true if the list is in ascending order
}
