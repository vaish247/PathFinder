import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;



/**
 * The game board for the pathfinder.
 * 
 * @author Vaishnav Ajith
 *
 */
public class Board {

  /**
   * The gameboard consisting of tiles.
   */
  private Tile[][] gameBoard;
  /**
   * Maximum length and width for the gameboard.
   */
  private final int BOARDSIZE;

  /**
   * Method that find the shortest route between 2 tiles. 
   * The route consists of an array of tiles, in a specific
   * order. This method uses the A* algorithm.
   *
   * @param start The first tile selected by the user.
   * @param target The second tile selected by the user.
   */
  public ArrayList<Tile> AStarSearch(Tile start, Tile target) {
   
    HashMap<Tile, ASNodes> recordPath = new HashMap<>();
    Queue<ASNodes> fringe = new PriorityQueue<>();
    ASNodes initialASNodes = new ASNodes(start, null, target);
    ArrayList<Tile> route = new ArrayList<Tile>();
    initialASNodes.setfTileDist();
    fringe.add(initialASNodes);
    while (!fringe.isEmpty()) {
      ASNodes next = fringe.poll();
      recordPath.put(next.getCurrentTile(), next);
      next.getCurrentTile().visitTile();

      if (next.getCurrentTile().equals(target)) {
        ASNodes current = new ASNodes(next.getCurrentTile(), next.getPrevTile(), target);
        recordPath.put(current.getCurrentTile(), current);
        while (current.getPrevTile() != null) {
          route.add(0, current.getCurrentTile());
          current = recordPath.get(current.getPrevTile());
        }
        fringe.clear();

      }
      for (Tile i : next.getCurrentTile().getNeigbourTiles()) {
        if (i.isVisitedTile() == false) {
          double gValue = next.getgTileDist();
          ASNodes ASElement = new ASNodes(i, next.getCurrentTile(), target);
          ASElement.setgTileDist(gValue);
          ASElement.setfTileDist();
          fringe.add(ASElement);
        }
      }
    }
    if (!route.isEmpty()) {
      route.add(0, start);
      for (Tile n : route) {
        setTile(n.getPos(), new PathTile(n.getPos()));
        loadNeighbour();
        System.out.println(n.getPos().getX() + "," + n.getPos().getY() + " TEE");
      }
    } else {
      System.out.println("Empty");
    }
    return route;

  }
  /**
   * Initializes the gameboard and the tiles within it.
   *
   * @param size determines how big the gameboard is.
   */
  public Board(int size) {
    this.BOARDSIZE = size;
    setGameBoard(new Tile[BOARDSIZE][BOARDSIZE]);
    intitializeBoard();
    loadNeighbour();
  }

  /**
   * Initalizeing all the tiles within a board to default state.
   */
  public void intitializeBoard() {
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        this.gameBoard[i][j] = new EmptyTile(new Position(j, i));
      }
    }
  }

  /**
   * String representation of board.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        sb.append("|");
        sb.append(this.gameBoard[i][j].toString());
        if (j == BOARDSIZE - 1) {
          sb.append("|");
          sb.append("\n");
        }
      }
    }
    return sb.toString();
  }
  /**
   * Method used for debugging.
   */
  public String toStringNeigbour() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        sb.append("|");
        sb.append(this.gameBoard[i][j].getNeigbourTiles().size());
        if (j == BOARDSIZE - 1) {
          sb.append("|");
          sb.append("\n");
        }
      }
    }
    return sb.toString();
  }

  /**
   * Finds the size of the board.
   * @return size of the board.
   */
  public int getBOARDSIZE() {
    return BOARDSIZE;
  }
  /**
   * Sets a tile to the gameboard. 
   *
   * @param pos The position of the inputted tile.
   * @param newTile The inputted tile.
   */
  public void setTile(Position pos, Tile newTile) {
    this.gameBoard[pos.getY()][pos.getX()] = newTile;
  }
  
  /**
   * Gets a tile from the gameboard.
   * @param pos The position of the requested tile.
   * @return The requested tile.
   */
  public Tile getTile(Position pos) {
    return gameBoard[pos.getY()][pos.getX()];
  }

  /**
   * Returns the entire gameboard
   * @return The gameboard
   */
  public Tile[][] getGameBoard() {
    return gameBoard;
  }

  /**
   * Returns an array of valid positons in the gameboard.
   * 
   * @param posList List of position
   * @return A list of valid positon.
   */
  public ArrayList<Position> filterPostion(ArrayList<Position> posList) {
    ArrayList<Position> filteredPos = (ArrayList<Position>) posList.stream()
        .filter(p -> p.validPositionSquare(BOARDSIZE - 1)).collect(Collectors.toList());
    return filteredPos;

  }

  /**
   * Helps loadneigbour method.
   * 
   * @param i Acts as a x value in Position.
   * @param j Acts as a y value in Position
   */
  public void loadNeighbourHelper(int i, int j) {
    ArrayList<Tile> neighbourTiles = new ArrayList<Tile>();
    Tile currentTile = this.gameBoard[j][i];
    Position pos1 = new Position(i + 1, j);
    Position pos2 = new Position(i, j + 1);
    Position pos3 = new Position(i - 1, j);
    Position pos4 = new Position(i, j - 1);
    Position pos5 = new Position(i + 1, j + 1);
    Position pos6 = new Position(i - 1, j - 1);
    Position pos7 = new Position(i + 1, j - 1);
    Position pos8 = new Position(i - 1, j + 1);
    ArrayList<Position> validPosition = new ArrayList<>(
        Arrays.asList(pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8));
    validPosition = filterPostion(validPosition);
    for (Position pos : validPosition) {
      if (!(getTile(pos).isBlockedTile())) {
        neighbourTiles.add(getTile(pos));
      }
    }
    currentTile.setNeigbourTiles(neighbourTiles);
  }

  /**
   * Loading all the neigbours onto the tile. So 
   * each tile now knows which tile is a neigbour to them.
   */
  public void loadNeighbour() {
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        loadNeighbourHelper(i, j);
      }
    }
  }
  
  /**
   * Makes all the tiles in the gameboard unvisited.
   */
  public void unvisitAll() {
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        Tile currentTile = this.gameBoard[j][i];
        currentTile.unVisitTile();
      }
    }
  }

  /**
   * Sets the current gameboard.
   * @param gameBoard The current gameboard.
   */
  public void setGameBoard(Tile[][] gameBoard) {
    this.gameBoard = gameBoard;
  }

}
