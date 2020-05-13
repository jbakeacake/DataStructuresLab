package DataStructuresLab.RBTree;

import java.awt.*;

/*
    RBNode : Class

    key : int >> The value associated with this RBNode

    parent, left, right : RBNode >> pointers to the parent and children of this RBNode
    color : Color (enum) >> denotes the color of this RBNode

    Basic data structure for RB-Tree. Holds color, data, and pointers to its left and right children.
     */
public class RBNode {
    public Color color;
    public int key;
    public RBNode parent,left,right;
    public RBNode(int key, RBNode sentinel)
    {
        this.key = key;
        this.left = sentinel; // We use a sentinel to identify our leaf nodes in this implementation so we can treat NIL nodes regularly
        this.right = sentinel; // Additionally, we use the same sentinel for each node
    }
    public RBNode()// For our sentinel
    {
        this.color = Color.BLACK;
    }
}