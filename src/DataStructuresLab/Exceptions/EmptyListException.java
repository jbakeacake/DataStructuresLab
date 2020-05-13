package DataStructuresLab.Exceptions;

public class EmptyListException extends Exception {
    public EmptyListException()
    {
        super("Empty List Exception: List is Empty");
    }
    public EmptyListException(String str)
    {
        super(str);
    }
}
