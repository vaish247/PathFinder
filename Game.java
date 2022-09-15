import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * Responsible for the game loop and game play.
 * @author ajith
 */
public class Game {
  /**
   * Game board used.
   */
  private Board game;
  /**
   * Shows how many tiles are selected
   */
  private int numberOfSelectTile = 0;
  /**
   * the first tile selected
   */
  private Tile start;
  /**
   * The last tile seleced
   */
  private Tile target;

  /**
   * Starts the game.
   * @param game
   */
  public Game(Board game) {
    this.game = game;
  }

  /**
   * Places the selcted tile on to the game board.
   * @param pos
   * @return 
   */
  public boolean placeSelectTile(Position pos) {
    if (numberOfSelectTile < 2) {
      Tile selectedTile1 = game.getTile(pos);
      selectedTile1 = new SelectedTile(pos);
      game.setTile(pos, selectedTile1);
      numberOfSelectTile++;
      game.loadNeighbour();
      System.out.println(game);

      if (numberOfSelectTile == 1) {
        this.start = game.getTile(pos);
      } else {
        this.target = game.getTile(pos);
      }
      return true;
    } else {
      return false;
    }

  }

  /**
   * Places the  wall tile on the game board.
   * @param pos
   * @return
   */
  public boolean placeWallTile(Position pos) {
    Tile selectedTile2 = game.getTile(pos);
    selectedTile2 = new WallTile(pos);
    game.setTile(pos, selectedTile2);
    game.loadNeighbour();

    int count = 0;
    for (int i = 0; i < game.getBOARDSIZE(); i++) {
      for (int j = 0; j < game.getBOARDSIZE(); j++) {
        if (game.getGameBoard()[i][j] instanceof SelectedTile) {
          count++;
        }
      }
    }
    this.numberOfSelectTile = count;

    System.out.println(game);
    return true;

  }

  /**
   * Places an empty tile on the game board.
   * @param pos
   * @return
   */
  public boolean placeEmptyTile(Position pos) {
    Tile selectedTile2 = game.getTile(pos);
    
    if(selectedTile2.equals(start)) {
      start = null;
      numberOfSelectTile--;
    }
    if(selectedTile2.equals(target)) {
      target = null;
      numberOfSelectTile--;
    }
    
    selectedTile2 = new EmptyTile(pos);
    game.setTile(pos, selectedTile2);
    game.loadNeighbour();
    System.out.println(game);

    int count = 0;
    for (int i = 0; i < game.getBOARDSIZE(); i++) {
      for (int j = 0; j < game.getBOARDSIZE(); j++) {
        if (game.getGameBoard()[i][j] instanceof SelectedTile) {
          count++;
        }
      }
    }
    this.numberOfSelectTile = count;

    return true;
  }

  /**
   * Places the path tiles on to the gameboard.
   * @return
   */
  public ArrayList<Tile> AStar() {
    if (numberOfSelectTile == 2) {
      ArrayList<Tile> route = game.AStarSearch(start, target);

      ArrayList<Tile> filteredRoute = (ArrayList<Tile>) route.stream()
          .filter(p -> !(p instanceof SelectedTile)).collect(Collectors.toList());

      filteredRoute.forEach((tile) -> game.setTile(tile.getPos(), new PathTile(tile.getPos())));
      game.loadNeighbour();
      game.unvisitAll();
      System.out.println(game);

      return filteredRoute;
    } else {
      return null;
    }
  }
  /**
   * Resets the game state.
   */
  public void reset() {
    this.start = null;
    this.target = null;
    this.numberOfSelectTile = 0;
    game.intitializeBoard();
  }

}
