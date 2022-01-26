import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Jack Cwynar (jec0089@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2019-03-29
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   
   List<String> EMPTY_LADDER = new ArrayList<>();
   
   TreeSet<String> lexicon;
   
   int wordCount;

   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         lexicon = new TreeSet<String>();
         
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            
            lexicon.add(str.toLowerCase());
            wordCount++;
            
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   
   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   public int getHammingDistance(String s1, String s2)   {
      
      int result = 0;
      
      if (s1.length() != s2.length())  {
         return -1;
      }
         
      char[] str1 = s1.toCharArray();
      char[] str2 = s2.toCharArray();
      
      for (int i = 0; i < s1.length(); i++)  {
         if (str1[i] != str2[i]) {
            result++;
         }
      }
            
      return result;
   }
   
   /**
   * Returns a minimum-length word ladder from start to end. If multiple
   * minimum-length word ladders exist, no guarantee is made regarding which
   * one is returned. If no word ladder exists, this method returns an empty
   * list.
   *
   * Breadth-first search must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   public List<String> getMinLadder(String start, String end)  {
   
      start = start.toLowerCase();
      end = end.toLowerCase();
      
      if (!lexicon.contains(start) || !lexicon.contains(end))  {
         return new LinkedList<String>();
      }
      
      if (start.length() != end.length()) {
         return new LinkedList<String>();
      }
         
      if (start.equals(end))  {
         return Arrays.asList(start);
      }
         
      HashSet<String> triedWords = new HashSet<>();
      triedWords.add(start);
      
      Node startNode = new Node(start, null);
      Deque<Node> q = new ArrayDeque<>();
      q.addLast(startNode);
      
      while (!q.isEmpty()) {
         Node current = q.removeFirst();
         List<String> neighbors = getNeighbors(current.word);
         
         for (String neighbor : neighbors)   {
            if (!triedWords.contains(neighbor)) {
               triedWords.add(neighbor);
               if (neighbor.equalsIgnoreCase(end)) {
                  return sequenceFromStart(new Node(neighbor, current));
               }
               q.addLast(new Node(neighbor, current));
            }
         }
      }
      return new LinkedList<String>();
   }
   
   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word)   {
      
      List<String> neighbor = new ArrayList<String>();
      
      if (word == null) {
         return EMPTY_LADDER;
      }
      
      for (String s: lexicon) {
         if (getHammingDistance(word, s) == 1)  {
            neighbor.add(s);
         }
      }
               
      return neighbor;
               
   }
   
   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount()  {
      
      return lexicon.size();
   }
     
   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */ 
   public boolean isWord(String str)  {
    
      if (lexicon.contains(str)) {
         return true;
      }
         
      return false;
   }
   
    /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) {
   
      if (sequence.isEmpty()) {
         return false;
      }
         
      for (int i = 0; i < sequence.size() - 1; i++)   {
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1)   {
            return false;
         }
      }
               
      for (int i = 0; i < sequence.size(); i++) {
         if (!lexicon.contains(sequence.get(i))) {
            return false;
         }
      }
                     
      return true;
   }
   
   private class Node   {
      
      private String word;
      private Node parent;
      
      public Node(String w, Node p) {
         word = w;
         parent = p;
      }
         
      @Override
         public String toString()  {
         return word;
      }
   }

   private List<String> sequenceFromStart(Node n)  {
      List<String> ladder = new LinkedList<>();
      ladder.add(n.word);
      Node p = n.parent;
      while (p != null) {
         ladder.add(0, p.word);
         p = p.parent;
      }
      return ladder;
   }
}

