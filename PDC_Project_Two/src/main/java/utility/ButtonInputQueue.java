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
    
    synchronized public int read()
    {
        if (!inputs.isEmpty())
        {
            int ret = inputs.dequeue();
            return ret;
        }
        
        return -1;
    }
    
    synchronized public void clear()
    {
        while (!inputs.isEmpty())
            inputs.dequeue();
    }
    
    synchronized public void addInput(int option)
    {
        inputs.enqueue(option);
    }
    
    synchronized public boolean isEmpty()
    {
        return inputs.isEmpty();
    }
    
    public static void main(String[] args)
    {
        // test
        
        ButtonInputQueue test = new ButtonInputQueue();
        
        for (int i = 0; i < 1000; i++)
        {
            test.addInput(i);
            test.read();
            test.clear();
        }
    }
}
