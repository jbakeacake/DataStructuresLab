package DataStructuresLab.BuiltInArrays;

import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.Exceptions.EmptyListException;

public class BuiltInArray implements IBasicDataStructure {

    private int Count, load_cap;
    private int[] bin;

    public BuiltInArray()
    {
        this.Count = 0;
        this.load_cap = 128;

        this.bin = new int[this.load_cap];
    }

    public BuiltInArray(int[] values)
    {
        this.Count = 0;
        this.load_cap = 128;
        this.bin = new int[this.load_cap];
        for(int n : values)
        {
            this.Insert(n);
        }
    }

    public int get(int idx) throws EmptyListException
    {
        if(isEmpty())
        {
            throw new EmptyListException();
        }
        else if(idx > this.Count-1)
        {
            throw new NullPointerException();
        }

        return this.bin[idx];
    }

    public int Count()
    {
        return this.Count;
    }

    @Override
    public String toString()
    {
        String rtn = "";
        for(int i = 0; i < this.Count; i++)
        {
            if(i == this.Count-1)
            {
                rtn = rtn.concat(Integer.toString(this.bin[i]));
            }
            else
            {
                rtn = rtn.concat(Integer.toString(this.bin[i]) + ", ");
            }
        }
        return rtn;
    }

    @Override
    public void Insert(int value) {
        if(isFull())
        {
            this.IncreaseCapacity();
        }
        else if(isEmpty())
        {
            this.bin[0] = value;
            this.Count++;
            return;
        }

        for(int i = 0; i <= this.Count; i++)
        {
            if(value <= this.bin[i])
            {
                // Make space for our insertion value -- shuffle our values right
                RightShuffleArrayFrom(i);
                //Insert values
                this.bin[i] = value;
                this.Count++;
                break;
            }
            else if(i == this.Count) //if the current value is larger than all values before it, insert at the 'end' of the array (i.e. the index after the last inserted value)
            {
                this.bin[i] = value;
                this.Count++;
                break;
            }
        }
    }

    private void IncreaseCapacity()
    {
        this.load_cap *= 2;
        int[] nBin = new int[this.load_cap];
        System.arraycopy(this.bin, 0, nBin, 0, this.Count);
        this.bin = nBin;
    }

    /*
    Delete(value : int) -> boolean

    Finds the matched value in the array and deletes it. Note that this does modify the Count of the array as deletions are made. When deleting
    from the array, you should copy the Count or use another variable to store the number of deletions to make.

    value : int >> Value to delete

    Return : boolean >> Returns true if the value was found and deleted.
     */
    @Override
    public boolean Delete(int value) throws EmptyListException {
        if(isEmpty()) throw new EmptyListException();
        //Find our value
        int high = this.Count;
        int low = 0;

        int mid = high / 2;
        while(mid >= 0 && mid <= high)
        {
            if(value < this.bin[mid])
            {
                high = mid;
                mid = low + (high - low) / 2;
            }
            else if(value > this.bin[mid])
            {
                low = mid;
                mid = low + (high - low) / 2;
            }
            else if(value == this.bin[mid]) //Found
            {
                //Take all values from indices [mid, this.Count - 1] and shuffle left overwriting the value we want to delete
                LeftShuffleArrayFrom(mid);
                this.Count--;
                return true;
            }
        }

        return false;
    }

    /*
    RightShuffleArrayFrom(start:int) -> void

    Shuffles all values from the insertion index (start) to the Count. [start, Count]. This allows our value to be inserted into the appropriate index.

    start : int >> value of index to be shifted over by 1

    Void
     */
    private void RightShuffleArrayFrom(int start)
    {
        //Starting at our deletion index + 1,
        //copy the current value, and the next value.
        int curr = this.bin[start];
        int next = this.bin[start+1];
        while(start < this.Count)
        {
            //Input the current value at the next position
            this.bin[start+1] = curr;
            start++;
            curr = next; //Set our new current to our previously copied 'next' value
            next = this.bin[start+1]; //Set our new next value to be after the newly set 'current' value
        }
    }

    /*
    LeftShuffleArrayFrom(start : int) -> void

    This function shuffles all elements from [start, Count] left one index, overwriting the value at our deletion index (start), effectively deleting the value.

    start : int >> Index of the node we're deleting

    Void
     */
    private void LeftShuffleArrayFrom(int start)
    {
        int curr = this.bin[start];
        while(start < this.Count)
        {
            this.bin[start] = this.bin[start+1];
            start++;
        }
    }

    /*
    Find(value : int) -> boolean

    Determine whether or not a value exists within our list. Utilizes a divide and conquer approach to search since values are inserted in order.

    value : int >> Value to be searched for.

    Return : boolean >> True if exists, false otherwise
     */
    @Override
    public boolean Find(int value) throws EmptyListException {
        if(isEmpty()) throw new EmptyListException();
        int high = this.Count;
        int low = 0;

        int mid = high / 2;
        while(mid >= 0 && mid <= high)
        {
            if(value < this.bin[mid])
            {
                high = mid;
                mid = low + (high - low) / 2;
            }
            else if(value > this.bin[mid])
            {
                low = mid;
                mid = low + (high - low) / 2;
            }
            else if(value == this.bin[mid]) //Found
            {
                return true;
            }
        }
        return false;
    }

    private boolean isFull()
    {
        if(this.Count == this.load_cap - 1)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if(this.Count == 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSorted() throws EmptyListException {
        if(isEmpty()) throw new EmptyListException();

        for(int i = 1; i < this.Count; i++)
        {
            if(this.bin[i-1] > this.bin[i])
            {
                return false;
            }
        }
        return true;
    }
}
