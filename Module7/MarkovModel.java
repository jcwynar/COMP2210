import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Jack Cwynar (jec0089@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   String firstKgram;

   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      int i = 0;
      int j = 0;
      int k = K;      
      firstKgram = sourceText.substring(0, k);
      while(i + k <= sourceText.length())
      {
         String value = "";
         String kgram = sourceText.substring(i, i + k);
         if(!model.containsKey(kgram))
         {
            int m = k;
            while(j + m < sourceText.length())
            {
               String str = sourceText.substring(j, j + m);
               if(j + k >= sourceText.length())
               {
                  value += '\u0000';
               }
               if(kgram.equals(str))
               {
                  value += sourceText.substring(j + m, j + m + 1);
               }
               j++;
            }
            model.put(kgram, value);
         }
         j = 0;
         i++;
      }
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return firstKgram;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      Random rand = new Random();
      int len = model.size();          
      int randIndex = rand.nextInt(len);
      int i = 0;  
      for(String kgram : model.keySet())
      {
         if(randIndex == i)
            return kgram;
         else
            i++;
      }
      
      return null;
   }

   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      
      Random rand = new Random();
      String result = "";
      int i = 0;
      int j = 0;
      int len = 0;
      
      for (String str : model.keySet())   {
      
         if (str.equals(kgram))  {
            result = model.get(kgram);
            len = result.length();
            
            if (len > 0)   {
               i = rand.nextInt(len) + 1;
            }
         }
      }
               
      j = i - 1;
               
      if (!result.equals("")) {
         return result.charAt(j);
      }
                  
      else  {
         return '\u0000';
      }
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
