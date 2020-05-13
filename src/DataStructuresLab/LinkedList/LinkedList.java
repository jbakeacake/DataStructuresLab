package DataStructuresLab.LinkedList;

import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.Exceptions.EmptyListException;

/*
LinkedList : class

This implementation is a singly linked list. This means that nodes can only travel down from the head of the list. Additionally,
this implementation only allows for integers to be stored into the nodes.
 */
public class LinkedList implements IBasicDataStructure {

    /*
        Node : class
        Basic node structure for LinkedList. Holds integer values.
     */
    class Node
    {
        public int data;
        public Node next;

        public Node(int data)
        {
            this.data = data;
            this.next = null;
        }
    }

    private int length;
    private Node head;

    public LinkedList()
    {
        this.length = 0;
        this.head = null;
    }

    public LinkedList(int[] data)
    {
        for(int n : data)
        {
            this.Insert(n);
        }
    }

    public int GetLength()
    {
        return this.length;
    }

    @Override
    public String toString()
    {
        if(this.isEmpty()) return "";
        Node curr = this.head.next;
        String rtn = "";
        rtn = rtn.concat(Integer.toString(this.head.data) + ", ");
        while(curr != null)
        {
            if(curr.next == null)
            {
                rtn = rtn.concat(Integer.toString(curr.data));
            }
            else
            {
                rtn = rtn.concat(Integer.toString(curr.data) + ", ");
            }

            curr = curr.next;
        }
        return rtn;
    }

    /*
    Insert(value : int) -> void

    Inserts a value into the list at its appropriate position in ASCENDING order.

    value : int >> Data to be inputted into the new node.

     */
    @Override
    public void Insert(int value) {
        if(isEmpty())
        {
            this.head = new Node(value); // If we're empty, set the head to the new node
            this.length++;
            return;
        }

        Node in = new Node(value);
        Node prev = null;
        Node curr = this.head;
        while(curr != null)
        {

            // If the 'in' node is smaller than the head, set this.head to in, and in.next to curr
            if(in.data <= this.head.data)
            {
                in.next = this.head;
                this.head = in;
                break;
            }
            else if(in.data <= curr.data) // Check if the current node is greater than/equal to -- if yes: set prev.next to in, set in.next to curr
            {
                prev.next = in;
                in.next = curr;
                break;
            }
            else if(curr.next == null) // Check if the next Node of curr is null, if so, we've reached the end of our list, input our new node there
            {
                curr.next = in;
                break;
            }

            prev = curr;
            curr = curr.next;
        }

        this.length++;
    }

    @Override
    public boolean Delete(int value) {
        if(this.head == null) return false;

        if(this.head.data == value)
        {
            this.head = this.head.next;
            this.length--;
            return true;
        }
        Node prev = null;
        Node curr = this.head;
        while(curr != null)
        {
            if(curr.data == value)
            {
                prev.next = curr.next;
                this.length--;
                return true;
            }

            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public boolean Find(int value) throws EmptyListException{
        if(isEmpty()) throw new EmptyListException("Empty List Exception : List is empty.");

        Node curr = this.head;
        while(curr != null)
        {
            if(curr.data == value)
            {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public int[] toArray() throws EmptyListException {
        if(isEmpty()) throw new EmptyListException("Empty List Exception: List is empty.");

        int[] rtn = new int[this.length];
        int counter = 0;
        Node curr = this.head;

        while(curr != null)
        {
            rtn[counter] = curr.data;
            curr = curr.next;
            counter++;
        }

        return rtn;
    }

    @Override
    public boolean isEmpty()
    {
        if(this.head == null && this.length == 0) return true;

        return false;
    }

    @Override
    public boolean isSorted() throws EmptyListException {
        if(isEmpty()) throw new EmptyListException("Empty List Exception: List is empty.");

        Node prev = null;
        Node curr = this.head;
        while(curr != null)
        {
            if(prev == null)
            {
                prev = curr;
                curr = curr.next;
                continue;
            }
            else if(prev.data > curr.data)
            {
                return false;
            }

            prev = curr;
            curr = curr.next;
        }
        return true;
    }

}
