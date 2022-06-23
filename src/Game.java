import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {
  private Board game;
  private int numberOfSelectTile = 0;
  private Tile start;
  private Tile target;

  public Game(Board game) {
    this.game = game;
  }

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
  
  public void reset() {
    this.start = null;
    this.target = null;
    this.numberOfSelectTile = 0;
    game.intitializeBoard();
  }

}
