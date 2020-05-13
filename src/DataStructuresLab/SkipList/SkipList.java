package DataStructuresLab.SkipList;

import DataStructuresLab.DS_Interfaces.IBasicDataStructure;
import DataStructuresLab.DS_Interfaces.ISkipList;
import DataStructuresLab.Exceptions.EmptyListException;

import java.util.Random;

public class SkipList implements ISkipList, IBasicDataStructure {

    final int MAX_LEVEL = 10;

    class SkipNode {
        int key, value;
        SkipNode[] forward; //pointer array to each node at different levels
        public SkipNode(int key, int value, int level)
        {
            this.key = key;
            this.value = value;
            this.forward = new SkipNode[level+1]; //Must be plus 1 in the case that our 'level' == 0
        }
        public SkipNode() // this will be the constructor for the head node of the skip list -- the header should be able to access every level of the skip list
        {
            this.key = 0;
            this.forward = new SkipNode[MAX_LEVEL+1];
        }
    }


    private int current_level = 0; // This refers to the current level that we're jump from/to -- i.e. if our current  level = 3, jump to the next level 3 node
    private int Count;
    private SkipNode head; //The head node will have access to the next node at every level of the skip list. If a node is not present on a level, the head will point to null.

    public SkipList()
    {
        this.head = new SkipNode();
        this.Count = 0;
    }

    @Override
    public int Count() {
        return this.Count;
    }

    @Override
    public String toString()
    {
        String keys = "KEY    : ";
        String[] levels = new String[MAX_LEVEL];
        for(int i = 0; i < levels.length; i++)
        {
            levels[i] = "Level " + i + ": "; //level 0 should always represent the next adjacent node in the list -- level i should represent the level i'th next node in the list
        }

        //Print level by level starting from the bottom, working left to right:
        /*
        We construct our string by working through skip list from bottom to top -- this gives an inverted picture of what our skip list structure looks like.
        Starting at 0, we loop through every forward node on the 0th level (i.e. all of them), then work our way up to the MAX_LEVEL. If a forward node is null, we represent that
        with " _ ", and if the forward node is pointing at the end of the list (null) we represent that with "[N]".

        This is an example of what this should look like (let key 0 represent the head):
        KEYS    : [0][1][2][3]
        Level 0 : [1][2][3][N]
        Level 1 : [2] _ [3][N]
        Level 2 : [3] _  _ [N]
        Level 3 : [N] _  _  _
         */
        SkipNode curr = this.head;
        while(curr != null)
        {
            keys += "[" + curr.key + "]";
            int level = 0;
            //Use tail end checking b/c each node has a 0th forward node
            do {
                if(level < curr.forward.length) //Check to see if our level is valid for this current nodes forward array
                {
                    if(curr.forward[level] == null) //If this the current level is pointing towards the end of the list
                    {
                        levels[level] += "[N]";
                    }
                    else
                    {
                        levels[level] += "[" + curr.forward[level].key + "]";
                    }
                }
                else // If the level is larger than our current node's forward array, fill it our with underscores
                {
                    /*
                    This is to try and maintain the structure of our skip list table. As we approach n-amount of digits, we need add additional characters
                    to better flesh out the null spaces. So, stringify the current key, take its length, and add the corresponding amount of underscores.
                     */
                    String digitStr = curr.key + "";
                    char[] digitArray = digitStr.toCharArray();
                    levels[level] += " _";
                    for(int i = 1; i <  digitArray.length; i++)
                    {
                        levels[level] += "_";
                    }
                    levels[level] += " ";
                }
                level++;
            }
            while(level < this.MAX_LEVEL);
            curr = curr.forward[0];
        }
        String res = "";
        res += keys + "\n";
        for(String s : levels)
        {
            res += s + "\n";
        }

        return res;
    }

    /*
    toArray(void) -> int[]

    Walks through the 0th level of our SkipList and inserts its values into an array.

    Return : int[] >> Array containing the values of our list
     */
    @Override
    public int[] toArray() throws EmptyListException {
        SkipNode curr = this.head.forward[0];
        int[] res = new int[this.Count];
        int i = 0;
        while(curr != null)
        {
            res[i] = curr.value;
            i++;
            curr = curr.forward[0]; //the next adjacent node at level 0
        }
        return res;
    }

    @Override
    public void Insert(int value)
    {
        //Basic insert, just use our current count as our key:
        Real_Insert(this.Count+1, value);
    }

    public void Real_Insert(int searchKey,  int newValue) {
        SkipNode[] update = new SkipNode[this.MAX_LEVEL+1]; //Create a list of pointers to nodes that will used in our new nodes list
        SkipNode curr = this.head;

        for(int i = this.current_level; i >= 0; i--) // Walk down the skip list, inserting the predecessors of our insertion node from the 'currentLevel' downto '0'
        {
            while(curr.forward[i] != null && curr.forward[i].key < searchKey) // See if we can skip forward at our current level, and determine if the node at this level is less than our searchKey
            {
                curr = curr.forward[i]; //if so, skip ahead
            }
            update[i] = curr; //if the next node on this level is greater than search key, insert the pointer to our current to our predecessor list
        } //by the end of this loop, we should be right behind our point of insertion

        curr = curr.forward[0]; //move our 'curr' pointer to the point of insertion
        if(curr != null && curr.key == searchKey) // if we're replacing a key, reassign the value
        {
            curr.value = newValue;
        }
        else // otherwise, we're either inserting a node into our list or at the end of our list
        {
            int nLvl = GetRandomLevel();
            if(nLvl > this.current_level) //if this new level is greater than the previous, highest-level node, insert pointers to the head of our list
            {
                for(int i = this.current_level+1; i <= nLvl; i++)
                {
                    update[i] = this.head;
                }
                this.current_level = nLvl; // update the current level to reflect this new greatest level
            }
            SkipNode insertionNode = new SkipNode(searchKey, newValue, nLvl);
            for(int i = 0; i <= nLvl; i++)
            {
                insertionNode.forward[i] = update[i].forward[i]; //append the successors to our insertion node
                update[i].forward[i] = insertionNode; //prepend the predecessors to our insertion node
            }
            this.Count++; //Increase list count
        }
    }

    /*
    GetRandomLevel(void) -> int

    Returns a random level for a newly inserted node. This number is calculated by how many 1's can be obtained during a coin flip (i.e. randomly selecting 0 or 1 every iteration).
    The number of coin flips that are allowed is equal to the max level of the skip list.

    Return : int >> the randomized level for this node
     */
    private int GetRandomLevel()
    {
        Random flip = new Random();
        int res = 0;
        int coinFlip = flip.nextInt(2);
        while(coinFlip != 0 && res < this.MAX_LEVEL)
        {
            res++;
            coinFlip = flip.nextInt(2);
        }
        return res;
    }

    @Override
    public boolean Delete(int searchKey) throws EmptyListException {
        if(isEmpty()) throw new EmptyListException();
        SkipNode[] predecessors = new SkipNode[this.MAX_LEVEL+1];
        //First, find the deletion node(s):
        SkipNode curr = this.head;
        /*
        Iterate through our list collecting all the node associated with our deletion node. We start from the
        top and work our way down to the 0th node just before our deletion node. We insert each node associated with
        this deletion node into our predecessors list so we can point the "next" node's at their respective levels to
        the next's of our deletion node.

        i.e. We're appending the successors of the deletion node to our predecessors list.
         */
        for(int i = this.current_level; i >= 0; i--) // Walk down each level until we hit the node just before our deletion node
        {
            while(curr.forward[i] != null && searchKey > curr.forward[i].key)
            {
                curr = curr.forward[i]; //Walk across our level
            }
            predecessors[i] = curr;
        }
        curr = curr.forward[0]; //Set our pointer to the supposed deletion node
        if(curr.key == searchKey) //If this key matches our searchKey, delete it
        {
            for(int i = 0; i <= this.current_level; i++) // loop through each forward node of our predecessors starting from 0
            {
                if(predecessors[i].forward[i] != curr) break; //if this predecessor's forward node is not pointing at our deletion node, we're at level that passes our deletion node, so break
                predecessors[i].forward[i] = curr.forward[i];
            }
            while(this.current_level > 0 && this.head.forward[this.current_level] == null) //Case where the deletion node had the highest levels, decrease level if this is the case
            {
                this.current_level--;
            }
            this.Count--;
            return true;
        }
        return false;
    }

    /*
    Find(searchKey : int) -> boolean

    Return true if the key is found and deleted.
    Walks down our skip list starting from the highest level (not our MAX_LEVEL), and determines
    the location of the node.

    searchKey : int >> the key of the node to be searched for

    Return : boolean >> (if found) ? true : false
     */
    @Override
    public boolean Find(int searchKey) throws EmptyListException {
        if(isEmpty()) throw new EmptyListException();

        SkipNode curr = this.head;
        for(int i = this.current_level; i >= 0; i--)
        {
            while(curr.forward[i] != null && curr.forward[i].key < searchKey)
            {
                curr = curr.forward[i];
            }
        }
        curr = curr.forward[0];

        return (curr.key == searchKey) ? true : false;
    }

    @Override
    public boolean isEmpty() {
        return (this.Count == 0) ? true : false;
    }

    @Override
    public boolean isSorted() throws EmptyListException {
        SkipNode curr = this.head;
        while(curr.forward[0] != null)
        {
            if(curr.key > curr.forward[0].key) return false;
            curr = curr.forward[0]; // get the next adjacent node
        }
        return true;
    }

    private boolean isMaxLevel()
    {
        return (this.current_level == this.MAX_LEVEL) ? true : false;
    }
}
