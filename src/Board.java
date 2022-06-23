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

public class Board {

  private Tile[][] gameBoard;
  private final int BOARDSIZE;

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

  public Board(int size) {
    this.BOARDSIZE = size;
    setGameBoard(new Tile[BOARDSIZE][BOARDSIZE]);
    intitializeBoard();
    loadNeighbour();
  }

  public void intitializeBoard() {
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        this.gameBoard[i][j] = new EmptyTile(new Position(j, i));
      }
    }
  }

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

  public int getBOARDSIZE() {
    return BOARDSIZE;
  }

  public void setTile(Position pos, Tile newTile) {
    this.gameBoard[pos.getY()][pos.getX()] = newTile;
  }

  public Tile getTile(Position pos) {
    return gameBoard[pos.getY()][pos.getX()];
  }

  public Tile[][] getGameBoard() {
    return gameBoard;
  }

  public ArrayList<Position> filterPostion(ArrayList<Position> posList) {

    ArrayList<Position> filteredPos = (ArrayList<Position>) posList.stream()
        .filter(p -> p.validPositionSquare(BOARDSIZE - 1)).collect(Collectors.toList());

    return filteredPos;

  }

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

  public void loadNeighbour() {
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        loadNeighbourHelper(i, j);
      }
    }
  }
  
  public void unvisitAll() {
    for (int i = 0; i < BOARDSIZE; i++) {
      for (int j = 0; j < BOARDSIZE; j++) {
        Tile currentTile = this.gameBoard[j][i];
        currentTile.unVisitTile();
      }
    }
  }

  public void setGameBoard(Tile[][] gameBoard) {
    this.gameBoard = gameBoard;
  }

}
