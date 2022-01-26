import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Creates a class that implements the WordSearchGame interface.
 *
 * @author Jack Cwynar (jec0089@auburn.edu)
 * @version 10/26/20
 */
 
public class WordSearchMachine implements WordSearchGame   {
   //String fileName = null;
   private TreeSet<String> lexicon = new TreeSet<String>();
   private String[][] board = new String[][] {
      {"E", "E", "C", "A"},
      {"A", "L", "E", "P"},
      {"H", "N", "B", "O"},
      {"Q", "T", "T", "Y"}
      };
   private int squareSize = 4;
   private boolean lexiconCalled = false;
      
    /**
     * Method to load a lexicon.
     */
   @Override
      public void loadLexicon(String fileName)  {
      
      if (fileName == null)   {
         throw new IllegalArgumentException();
      }
      
      File file = new File(fileName);
      Scanner s = null;
      
      try   {
         s = new Scanner(file);
         while (s.hasNext())  {
            lexicon.add(s.next().toLowerCase());
         }
      } catch (FileNotFoundException e)   {
         throw new IllegalArgumentException();
      } finally   {
         if (s != null) {
            s.close();
         }
      }
    //System.out.println(lexicon.size());
      lexiconCalled = true;
   }
    /**
     * Method to set the board.
     */
   @Override
   public void setBoard(String[] letterArray)   {
   
      if (letterArray == null)   {
         throw new IllegalArgumentException();
      }
      double n = Math.sqrt((double) letterArray.length);
      
      if (n == Math.round(n)) {
         int n2 = (int) n;
         board = new String[n2][n2];
         
         for (int i = 0; i < n2; i++)  {
            for (int j = 0; j < n2; j++)  {
               board[i][j] = letterArray[(i * n2 + j)];
            }
         }
       //board = Arrays.copyOf(letterArray, letterArray.length);
         squareSize = n2;
      }
      else throw new IllegalArgumentException();
   }
    /**
     * Method to get the board.
     */
   @Override
   public String getBoard()   {
   
      StringBuilder sb = new StringBuilder();
      
      for (int i = 0; i < squareSize; i++)   {
         for (int j = 0; j < squareSize; j++)   {
            sb.append(board[i][j] + "");
            
            if (j == squareSize - 1)   {
               sb.append("br ");
            }
         }
      }
      String s = sb.toString();
      return s;
   }
    /**
     * Method to get all the valid words in the lexicon
     */
   @Override
   public SortedSet<String> getAllScorableWords(int minimumWordLength)  {
   
      if (!lexiconCalled)  {
         throw new IllegalStateException();
      }
         
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      boolean[][] visited = new boolean[squareSize][squareSize];
      TreeSet<String> words = new TreeSet<String>();
            
      for (int a = 0; a < squareSize; a++)   {
         for (int b = 0; b < squareSize; b++)   {
            getWords(minimumWordLength, a, b, "", visited, words);
         }
      }
      return words;
   }
   
   /**
    * Private method to get words in lexicon
    */
   private void getWords(int minLength, int i, int j, 
      String str, boolean[][] vis, TreeSet<String> word) {
      
      vis[i][j] = true;
      str = str + board[i][j];
      System.out.println("Current string: " + str);
      System.out.println(str + "");
         
      if (isValidWord(str) && str.length() >= minLength) {
         word.add(str);
      }
            
      if (isValidPrefix(str)) {
         for (int row = i - 1; row <= i + 1 && row < squareSize; row++) {
            for (int col = j - 1; col <= j + 1 && col < squareSize; col++) {
               if (row >= 0 && col >= 0 && !vis[row][col])  {
                  System.out.println("Valid Prefix");
                  getWords(minLength, row, col, str, vis, word);
                  System.out.println("Should come after valid run");
               }
            }
         }
      }
      
      // Erase current character from string and mark
      // visited of current cell as false
      str = str.substring(0, str.length() - 1);
      vis[i][j] = false;
      System.out.println("this thing got run");
   }
   
    /**
     * Method to get player's score.
     */
   @Override
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength)   {
   
      if (!lexiconCalled)  {
         throw new IllegalStateException();
      }
      
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      Iterator<String> itr = words.iterator();
      int total = 0;
      while (itr.hasNext())  {
         String s = itr.next();
            
         if (s.length() <= minimumWordLength && isValidWord(s) && !isOnBoard(s).isEmpty())   {
            total += 1;
               
            if (s.length() > minimumWordLength) {
               total += s.length() - minimumWordLength;
            }
         }
      }
      return total;
   }
   
  /**
   * Determines if the given word is in the lexicon.
   */
   @Override
   public boolean isValidWord(String wordToCheck)  {
      wordToCheck = wordToCheck.toLowerCase();
      
      if (wordToCheck == null)   {
         throw new IllegalArgumentException();
      }
      else if (!lexiconCalled)   {
         throw new IllegalStateException();
      }
      else if (lexicon.contains(wordToCheck))   {
         return true;
      }
      else 
         return false;
   }
   
   /**
    * Determines if there is at least one word in lexicon 
    * with given prefix.
    */
   @Override
    public boolean isValidPrefix(String prefixToCheck) {
    
      prefixToCheck = prefixToCheck.toLowerCase();
      System.out.println("falsefalse");
      
      if(prefixToCheck.equals(null)) {
         throw new IllegalArgumentException();
      }
      
      if(!lexiconCalled) {
         throw new IllegalStateException();
      }
      
      //TreeSet<String> l = new TreeSet<String>(lexicon.tailSet(prefixToCheck));
      String s = lexicon.ceiling(prefixToCheck);
      System.out.println("s = " + s);
      
      if(s.equals(null) || !s.startsWith(prefixToCheck)) {
         return false;
      }
      
      else if(s.startsWith(prefixToCheck) || s.equals(prefixToCheck)) {
         System.out.println("THis is working lol");
         return true;
      }
      return false;
   }
   
   /**
    * Determines if word is on the board.
    */
   @Override
    public List<Integer> isOnBoard(String wordToCheck) {
      if(wordToCheck.equals(null)) {
         throw new IllegalArgumentException();
      }
      if(!lexiconCalled) {
         throw new IllegalStateException();
      }
      boolean[][] visited = new boolean[squareSize][squareSize];
      ArrayList<Integer> list = new ArrayList<Integer>();
      for(int a = 0; a < squareSize; a++) {
         for(int b = 0; b < squareSize; b++) {
            wordOnBoard(a,b,"",wordToCheck,visited,list);
         }
      }
      //wordOnBoard(0,0,"",wordToCheck,visited,list);
      return list;
   }
   
   /**
    * Determines if word is on the board.
    */
   private void wordOnBoard(int i, int x, String str, String found, boolean[][] vis, ArrayList<Integer> word) {
      vis[i][x] = true;
      str = str + board[i][x];
      if(str.equals(found)) {
         for(int j = 0; j < squareSize; j++) {
            for(int k = 0; k < squareSize; k++) {
               if(vis[j][k] == true) {
                  word.add(j * squareSize + k);
               }
            }
         }
      }
      if(found.startsWith(str)) {
         for (int row = i-1; row <= i+1 && row < squareSize; row++) {
            for (int col = x-1; col <= x+1 && col < squareSize; col++) {
               if (row >= 0 && col >= 0 && !vis[row][col]) {
                  wordOnBoard(row, col, str, found, vis, word);
               }
            }
         }
      }
      // Erase current character from string and mark visited
      // of current cell as false
      str = str.substring(0, str.length()-1);
      vis[i][x] = false;
   }
   
   /**
    * Compares prefixes of different words.
    */
   private class ComparePrefix implements Comparator<String> {
   
      @Override
        public int compare(String o1, String o2) {
         // TODO Auto-generated method stub
         return 0;
      }
   }
}
