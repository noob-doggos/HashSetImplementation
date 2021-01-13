// Implements a set of integers using a hash table.
// The hash table uses separate chaining to resolve collisions.
public class MyHashSet
{
    private static final double MAX_LOAD_FACTOR = 0.75;
    private HashEntry[] elementData;
    private int size;

    // Constructs an empty set.
    public MyHashSet()
    {
        elementData = new HashEntry[10];
        size = 0;
    }

    // Adds the given element to this set
    // By: Victor
    public void add(int value)
    {
        int hashcode = hashFunction(value);

        if (elementData[hashcode] == null)
        {
            elementData[hashcode] = new HashEntry(value);
            size++;
        }
        else
        {
            HashEntry cur = elementData[hashcode];
            while (cur != null)
            {
                if (cur.data == value) return;
                if (cur.next == null)
                {
                    cur.next = new HashEntry(value);
                    size++;
                    return;
                }
                cur = cur.next;
            }
        }
        
        if (loadFactor() >= MAX_LOAD_FACTOR)
        {
            rehash();
        }
    }

    // Removes all elements from the set.
    // By: Victor
    public void clear()
    {
        elementData = new HashEntry[10];
        size = 0;
    }

    // Returns true if the given value is found in this set.
    // By: Victor
    public boolean contains(int value)
    {
        HashEntry cur = elementData[hashFunction(value)];

        while (cur != null)
        {
            if (cur.data == value)
            {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // Returns true if there are no elements in this queue.
    // by: Clyde
    public boolean isEmpty()
    {
        return size == 0;
    }

    // Removes the given value if it is contained in the set.
    // If the set does not contain the value, has no effect.
    // by: Clyde
    public void remove(int value)
    {        
        int hashcode = hashFunction(value);
        HashEntry cur = elementData[hashcode];  
        HashEntry prev = null;      
        while(cur != null)
        {            
            if(cur.data != value)
            {
                prev = cur; 
                cur = cur.next;                  
            } else {
                if(prev == null)
                {
                    elementData[hashcode] = cur.next;
                } else {                
                    prev.next = cur.next;
                }
                size--;
                break;
            }
        }        
    }

    // Returns the number of elements in the queue.
    // by: Clyde
    public int size()
    {       
        return size;
    }

    // Returns the preferred hash bucket index for the given value.
    // by: Eric
    private int hashFunction(int value)
    {
        // TODO: return the abs(value) modulo elementData.length.
        return Math.abs(value % elementData.length);
    }

    // by: Eric
    private double loadFactor()
    {
        // TODO: return size instance variable / elementData.length, but convert the divisor or dividend to double.
        return (double) this.size / elementData.length;
    }

    // Resizes the hash table to twice its original size/
    // by: Eric
    private void rehash()
    {
        // TODO:
        // assign HashEntry[] oldData to elementData.
        // reinitialize elementData to a new HashArray with size 2 * oldData.length.
        // set size to 0.
        // then, for each HashEntry in oldData:
        //  if the HashEntry is not null,
        //      assign HashEntry cur to the current HashEntry.
        //          while cur is not NULL:
        //              call add(cur.data).
        //              assign cur to cur.next.
        HashEntry[] oldData = elementData;

            // formation of new table
            elementData = new HashEntry[2 * oldData.length];
            size = 0;
        
            // copying the table
            for (HashEntry curEntry : oldData)
            {
                HashEntry cur = curEntry;
                while (cur != null)
                {
                    add(cur.data);
                    cur = cur.next;
             }
         }
        
    }
    
    // Returns a string representation of this queue, such as "[10, 20, 30]";
    // The elements are not guaranteed to be listed in sorted order.
    public String toString()
    {
        String result = "[";
        boolean first = true;
        if (!isEmpty())
        {
            for (int i = 0; i < elementData.length; i++)
            {
                HashEntry current = elementData[i];
                while (current != null)
                {
                    if (!first)
                    {
                        result += ", ";
                    }
                    result += current.data;
                    first = false;
                    current = current.next;
                }
            }
        }
        return result + "]";
    }

    // Represents a single value in a chain stored in one hash bucket.
    private class HashEntry
    {
        public int data;
        public HashEntry next;

        public HashEntry(int data)
        {
            this(data, null);
        }

        public HashEntry(int data, HashEntry next)
        {
            this.data = data;
            this.next = next;
        }
    }
}
