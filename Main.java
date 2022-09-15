import java.io.IOException;
import java.util.Scanner;
/**
 * Executes game.
 * @author ajith
 *
 */
public class Main {
  
  /**
   * Starts Game 
   * @param args
   * @throws IOException
   */
  public static void main(String args[]) throws IOException {
    Board game = new Board(8);
    Display display = new Display(game);    
  }


}
