/**
 * This interface is from a lab activity from my Data Structures and Algorithms paper.
 */

package utility;

import java.util.NoSuchElementException;

public interface QueueADT<E>
{
   // adds one element to the rear of this queue
   public void enqueue(E element);
   // removes and returns the front element of the queue
   public E dequeue() throws NoSuchElementException;
   // returns without removing the front element of this queue
   public E first() throws NoSuchElementException;
   // returns true if this queue contains no elements
   public boolean isEmpty();
   // returns the number of elements in this queue
   public int size();
}