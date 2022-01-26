import java.util.Arrays;
import java.util.Comparator;
   
   /**
    * Creates the BinarySearch class with two search methods.
    *
    * @author Jack Cwynar (jec0089@auburn.edu)
    * @version 9/30/20
    */
    
public class BinarySearch {
      
   /**
    * Returns the index of the first key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    *
    * @param a for array
    * @param key of type key
    * @param comparator
    * @throws NullPointerException
    * @return lo
    */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator)
         throws NullPointerException   {
      if (a == null || key == null || comparator == null)   {
         throw new NullPointerException();
      }
      if (a.length == 0)   {
         return -1;
      }
      int lo = 0;
      int hi = a.length - 1;
      while (lo <= hi)   {
         int mid = (lo + hi) / 2;
         if (comparator.compare(a[mid], key) < 0)  {
            lo = mid + 1;
         }
         else  {
            hi = mid;
         }
      }
      return lo;
   }
   
    /**
     * Returns the index of the last key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     *
     * @param a for array
     * @param key of type key
     * @param comparator
     * @throws NullPointerException
     * @return hi
     */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator)
         throws NullPointerException   {
      if (a == null || key == null || comparator == null)   {
         throw new NullPointerException();
      }
      if (a.length == 0)   {
         return -1;
      }
      int lo = 0;
      int hi = a.length - 1;
      while (lo <= hi)   {
         int mid = ((lo + hi) / 2) + 1;
         if (comparator.compare(key, a[mid]) < 0)  {
            hi = mid - 1;
         }
         else  {
            lo = mid;
         }
      }
      return hi;
   }
}