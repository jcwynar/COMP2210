import java.util.Arrays;
   
   
   /**
    * Creates Autocomplete class to provide autocomplete functionality.
    *
    * @author Jack Cwynar (jec0089@auburn.edu)
    * @version 9/30/20
    */
       
public class Autocomplete {
   static Term[] terms;
   
   /**
    * Initializes a data structure from the given arra of terms.
    * This method throws a NullPointerException if terms is null.
    */
   public Autocomplete(Term[] terms)  {
      if (terms == null)   {
         throw new NullPointerException();
      }
      for (Term tmp : terms)  {
         if (tmp == null)  {
            throw new NullPointerException();
         }
      }
      terms = terms.clone();
      Arrays.sort(terms);
   }
   
   /**
    * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
    */
   public Term[] allMatches(String prefix)  {
      if (prefix == null)  {
         throw new NullPointerException();
      }
      int start = BinarySearch.<Term>firstIndexOf(terms, new Term(prefix, 1), Term.byPrefixOrder(prefix.length()));
      int end = BinarySearch.<Term>lastIndexOf(terms, new Term(prefix, 1), Term.byPrefixOrder(prefix.length())) + 1;
      Term[] matches = Arrays.copyOfRange(terms, start, end);
      Arrays.sort(matches, Term.byDescendingWeightOrder());
      return matches;
      
   }
}