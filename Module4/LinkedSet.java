import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Jack Cwynar (jec0089@auburn.edu)
 * @version 10/12/20
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
   
      if (element == null || contains(element) == true)  {
         return false;
      }
      
      else if (size() == 0) {
         front = new Node(element);
         rear = front;
         size++;
      }
         
      else if (size() == 1 && front.element.compareTo(element) < 0) {
         front.next = new Node(element);
         rear = front.next;
         rear.prev = front;
         size++;
      }
      else if (size() == 1 && front.element.compareTo(element) > 0)  {
         Node f = front;
         f.prev = new Node(element);
         front = f.prev;
         front.next = f;
         f.prev = front;
         rear = front.next;
         size++;
      }
      
      else  {
         Node n = front;
         while (n.next != null)  {
            if (n.element.compareTo(element) < 0)  {
               n = n.next;
            }
            else  {
               if (first(element) == true)   {
                  Node m = n;
                  m.prev = new Node(element);
                  n = m.prev;
                  n.next = m;
                  front = n;
                  size++;
                  return true;
               }
               Node m = new Node(element);
               m.prev= n.prev;
               n.prev.next = m;
               m.next = n;
               n.prev = m;
               size++;
               return true;
            }
         }
         if (n.next == null)  {
            if (size() == 2 && rear.element.compareTo(element) > 0) {
               Node middle = new Node(element);
               rear.prev = middle;
               front.next = middle;
               middle.next = rear;
               middle.prev = front;
               size++;
            }
            else if (rear.element.compareTo(element) > 0)   {  
               Node m = new Node(element);
               m.prev = n.prev;
               m.next = n;
               n.prev.next = m;
               n.prev = m;
               size++;
            }
            else  {
               Node m = new Node(element);
               n.next = m;
               m.prev = n;
               rear = m;
               size++;
            }
         }
      }
      return true;
   }
   
   /**
    * Method to locate node that contains specified element.
    */
   private Node locate(T element)  {
      Node n = front;
      while (n != null) {
         if (n.element.equals(element))  {
            return n;
         }
         n = n.next;
      }
      return null;
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
   
      Node n = locate(element);
      
      if (isEmpty() || n == null) {
         return false;
      }
      else  {
         if (n.element == front.element && size() == 1)  {
            front = null;
            rear = null;
            size--;
         }
         else if (n.element == front.element && size() > 1) {
            front = front.next;
            front.prev = null;
            size--;
            return true;
         }
         else  {
            n.prev.next = n.next;
            if (n.next ==  null) {
               rear = n.prev;
               n.prev.next = null;
            }
            else  {
               n.next.prev = n.prev;
            }
            size--;
            return true;
         }
      }
      return true;
   }

   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      
      Node temp = front;
      while (temp != null) {
         if (temp.element != null && temp.element.equals(element))   {
            return true;
         }
         temp = temp.next;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      
      if (size == s.size() && complement(s).size() == 0) {
         return true;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
   
      if (size == s.size() && complement(s).size() == 0) {
         return true;
      }
      return false;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      
      if (s == null) {
         throw new NullPointerException();
      }
         
      LinkedSet<T> returnSet = new LinkedSet<T>();
      Node t = front;
         
      while (t != null) {
         returnSet.add(t.element);
         t = t.next;
      }
            
      Iterator<T> i = s.iterator();
            
      while (i.hasNext())  {
         returnSet.add(i.next());
      }
      return returnSet;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      
      if (s == null) {
         throw new NullPointerException();
      }
      
      LinkedSet<T> returnSet = new LinkedSet<T>();
      Node t = front;
      
      while (t != null) {
         returnSet.add(t.element);
         t = t.next;
      }
         
      Iterator<T> i = s.iterator();
         
      while (i.hasNext())  {
         returnSet.add(i.next());
      }
      return returnSet;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      
      if (s == null) {
         throw new NullPointerException();
      }
      
      LinkedSet<T> returnSet = new LinkedSet<T>();
      Node t = front;
      
      while (t != null) {
         if (s.contains((T)t.element)) {
            returnSet.add((T)t.element);
         }
         t = t.next;
      }
      return returnSet;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      
      if (s == null) {
         throw new NullPointerException();
      }
      
      LinkedSet<T> returnSet = new LinkedSet<T>();
      Node t = front;
      
      while (t != null) {
         if (s.contains((T)t.element))  {
            returnSet.add((T)t.element);
         }
         t = t.next;
      }
      return returnSet;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      
      if (s == null) {
         throw new NullPointerException();
      }
         
      LinkedSet<T> returnSet = new LinkedSet<T>();
      Node t = front;
         
      while (t != null) {
         if (!s.contains((T)t.element))   {
            returnSet.add((T)t.element);
         }
         t = t.next;
      }
      return returnSet;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      
      if (s == null) {
         throw new NullPointerException();
      }
         
      LinkedSet<T> returnSet = new LinkedSet<T>();
      Node t = front;
         
      while (t != null) {
         if (!s.contains((T)t.element))   {
            returnSet.add((T)t.element);
         }
         t = t.next;
      }
      return returnSet;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      
      Iterator itr = new LinkedIterator();
      return itr;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      
      Iterator<T> descending = 
         new Iterator<T>()   {
            Node r = rear;
            Node i = r;
            @Override
            public T next()   {
               i = r;
               r = r.prev;
               return i.element;
            }
            @Override
               public boolean hasNext()   {
               return !(i == null || i.prev == null);
            }
         };
      return descending;
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      
      Iterator<Set<T>> powerSet = 
         new Iterator<Set<T>>() {
            int index = 0;
            Node r = rear;
            Node j = r;
            LinkedSet<T> pset;
            @Override
            public LinkedSet<T> next() {
               LinkedSet<T> pset = new LinkedSet<T>();
               String binary = Integer.toBinaryString(index);
               r = rear;
               for (int i = 0; i < binary.length(); i++) {
                  if (Character.getNumericValue(binary.charAt(binary.length() - 1 - i)) == 1)   {
                     pset.add(r.element);
                  }
                  j = r;
                  if (r != null) {
                     r = r.prev;
                  }
               }
               //if (r.element != front.element)  {
                  //r = rear;
               //}
               index++;
               return pset;
            }
            @Override
                           public boolean hasNext()   {
               return index<Math.pow(2, size);
            }
         };
      return powerSet;
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   private boolean first(T element) {
      
      Node n = new Node(element);
      Node m = front;
      while (m.next != null)  {
         if (n.element.compareTo(m.element) < 0)   {
            m = m.next;
         }
         else  {
            return false;
         }
      }
      return true;
   }
   
   private class LinkedIterator implements Iterator<T>  {
   
      private Node current = front;
      
      public boolean hasNext()   {
         return current != null;
      }
         
      public T next()   {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         
         T result = current.element;
         current = current.next;
         return result;
      }
               
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
   
   ////////////////////
   // Nested classes //
   ////////////////////

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}
