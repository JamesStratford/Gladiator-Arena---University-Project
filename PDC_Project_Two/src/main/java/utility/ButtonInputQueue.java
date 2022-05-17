package utility;


/**
 *  Custom input stream for MainGUI handling of switch cases.
 * 
 * @author James Stratford 21129223
 */
public class ButtonInputQueue
{
    private ArrayQueue<Integer> inputs;
    
    public ButtonInputQueue()
    {
        inputs = new ArrayQueue();
    }
    
    public int read()
    {
        if (!inputs.isEmpty())
        {
            return inputs.dequeue();
        }
        
        return -1;
    }
    
    public void addInput(int option)
    {
        inputs.enqueue(option);
    }
    
    public boolean isEmpty()
    {
        return inputs.size() == 0;
    }
}
