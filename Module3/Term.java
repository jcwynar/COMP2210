import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
      
   /**
    * Represents a (query, weight) pair for use in
    * an autocomplete program.
    *
    * @author Jack Cwynar (jec0089@auburn.edu)
    * @version 9/30/20
    */
public class Term implements Comparable<Term>  {
   private String query;
   private long weight;
  
  /**
   * Initialize a term with the given query and weight.
   * Throws a NullPointerException if query is null,
   * and an IllegalArgumentException if weight is negative.
   *
   * @param query for search query
   * @param weight for query weight
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
   public Term(String query, long weight) throws NullPointerException,
     IllegalArgumentException   {
     
      if (query == null) {
         throw new NullPointerException();
      }
      if (weight < 0)   {
         throw new IllegalArgumentException();
      }
      this.query = query;
      this.weight = weight;
   }
   
   /**
    * Compares the two terms in descending order of weight.
    */
   public static Comparator<Term> byDescendingWeightOrder()   {
      return 
         new Comparator<Term>() {
            @Override
            public int compare(Term t1, Term t2)   {
               return Long.compare(t2.weight, t1.weight);
            }
         };
   }   
   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero. 
    * @param length for length of prefix
    * @throws IllegalArgumentException
    */
   public static Comparator<Term> byPrefixOrder(int length) throws IllegalArgumentException   {
   
      if (length <= 0)  {
         throw new IllegalArgumentException();
      }
      return 
         new Comparator<Term>() {
            @Override
            public int compare(Term t1, Term t2)   {
               int k1 = t1.query.length();
               int k2 = t2.query.length();
               int j = Math.min(k2, Math.min(k1, length));
               String s1 = t1.query.substring(0, j);
               String s2 = t2.query.substring(0, j);
               return s1.compareTo(s2);
            }
         };
   }
   
   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
    public int compareTo(Term other)   {
      return query.compareTo(other.query);
   }
   
   /**
    * Returns a String representation of this term.
    */
   @Override
    public String toString()  {
      return query + "\t" + weight;
   }
   
   /**
    * Driver method.
    */
   public static void main(String[] args)  {
      Term t1 = new Term("azcei", 12);
      Term t2 = new Term("abct", 14);
      ArrayList<Term> a = new ArrayList<>();
      a.add(t1);
      a.add(t2);
      Collections.sort(a, byPrefixOrder(2));
      for (Term tmp : a)   {
         System.out.println(tmp.query);
      }
   }
}
