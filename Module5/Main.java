import java.util.TreeSet;

/**
 * Provides main method to test WordSearch program.
 *
 * @author Jack Cwynar
 * @version 10/26/20
 */
public class Main   {
   
   public static void main(String[] args) {
      WordSearchGame game = WordSearchGameFactory.createGame();
      System.out.println(game.getBoard());
      game.loadLexicon("words.txt");
      TreeSet<String> list = new TreeSet<String>(game.getAllScorableWords(3));
      System.out.println(list.size() + "");
   }
}