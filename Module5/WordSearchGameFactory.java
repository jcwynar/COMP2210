
/**
 * Provides a factory method for creating word search games.
 *
 * @author Jack Cwynar (jec0089@auburn.edu)
 * @version 10/26/20
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGame createGame() {
      return new WordSearchMachine();
   }

}
