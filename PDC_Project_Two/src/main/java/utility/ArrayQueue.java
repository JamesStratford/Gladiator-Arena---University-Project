/**
 * This class is a lab activity I made in my Data Structures and Algorithms paper.
 */
package utility;

import java.util.NoSuchElementException;

/**
 *
 * @author jestr
 * @param <E>
 */
public class ArrayQueue<E> implements QueueADT<E>
{
    private final int DEFAULT_CAPACITY = 15;
    private int currentCapacity;
    private int front;
    private int rear;
    private E[] queue;
    private int size;
    
    public ArrayQueue()
    {
        queue = (E[])(new Object[DEFAULT_CAPACITY]);
        currentCapacity = DEFAULT_CAPACITY;
        front = 0;
        rear = 0;
        size = 0;
    }

    @Override
    public void enqueue(Object element)
    {
        size++;
        if (rear < currentCapacity)
        {
            queue[rear++] = (E) element;
        }
        else if (front >= 0)
        {
            if (rear >= front)
                rear = 0;
            else
                rear++;
            queue[rear++] = (E) element;
        }
        else if (rear + 1 == front ^ (rear == currentCapacity && front == 0))
        {
            expandCapacity();
            rear = 0;
            queue[rear++] = (E) element;
        }
    }

    @Override
    public E dequeue() throws NoSuchElementException
    {
        if (size == 0)
        {
//            return null;
            throw new NoSuchElementException();
        }

        size--;
        E frontTemp = queue[front];
        queue[front] = null;

        if (front < currentCapacity -1)
        {
            front++;
        }
        else
        {
            front = 0;
        }

        return frontTemp;
    }

    @Override
    public E first() throws NoSuchElementException
    {
        return queue[front];
    }

    @Override
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    @Override
    public int size()
    {
        return this.size;
    }
    
    public void expandCapacity()
    {
            int newSize = queue.length * 2;
            E[] newArray = (E[])(new Object[newSize]);
            int k = front;
            for (int i = 0; i < queue.length; i++)
            {
                newArray[i] = queue[k++];
                
                if (k == currentCapacity)
                {
                    k = 0;
                }
            }
            
            front = 0;
            rear = queue.length-1;

            currentCapacity = newSize;
            queue = newArray;
    }
    
    public static void main(String[] args)
    {
        ArrayQueue<Integer> test = new ArrayQueue<>();
        
        for (int i = 0; i < 10; i++)
        {
            test.enqueue(i);
        }
        
        for (int i = 0; i < 50; i++)
        {
            Integer removed = (Integer)test.dequeue();
            test.enqueue(removed);
            test.enqueue(i+10);
            System.out.println(test.first());
        }  
    }
}
