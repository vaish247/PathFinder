import java.util.ArrayList;
import java.util.Objects;

/**
 * A single tile used in a gameboard.
 */
public abstract class Tile {

  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Tile other = (Tile) obj;
    return blockedTile == other.blockedTile && Objects.equals(id, other.id)
        && Objects.equals(neigbourTiles, other.neigbourTiles) && Objects.equals(pos, other.pos)
        && visitedTile == other.visitedTile;
  }
  
  /**
   * unique identifier for tile.
   */
  private String id;
  /**
   * Unique position of the tile.
   */
  private Position pos;
  /**
   * Determines if other tiles can overide this tile.
   */
  private boolean blockedTile;
  /**
   * Determines if this tile has been visited by A* algorithm
   */
  private boolean visitedTile;
  /**
   * A list of each neighboring tiles.
   */
  private ArrayList<Tile> neigbourTiles;

  /**
   * Inititalizeing tile
   * @param id Unique tile identifier
   * @param pos Position of tile
   * @param blockedTile Determines whether tile can be overided.
   */
  public Tile(String id, Position pos, boolean blockedTile) {
    this.setId(id);
    this.setPos(pos);
    this.setBlockedTile(blockedTile);
    this.unVisitTile();
    
  }

  @Override
  public String toString() {
    return id;
  }
  
  /**
   * Calculates shortest distance between the target tile and the current 
   * tile.
   * @param targetNode Targrt tile.
   * @return shortest distance between the target tile and the current tile.
   */
  public double distanceCalc(Tile targetNode) {
    assert (this.getPos() == null);
    assert(targetNode != null);
    int x = getPos().getX()- targetNode.getPos().getX();
    int y = getPos().getY()- targetNode.getPos().getY();
    double x2 = Math.pow(x, 2);   
    double y2 = Math.pow(y, 2);
    return (double) Math.sqrt(x2+y2);
  }
  
  /**
   * Get id of the tile
   * @return id
   */
  public String getId() {
    return id;
  }
  /**
   * Sets id of the tile
   * @param id: new id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the position of the tile.
   * @return position of the tile.
   */
  public Position getPos() {
    return pos;
  }

  /**
   * Sets the position of the tile.
   * @param pos: position of the tile
   */
  public void setPos(Position pos) {
    this.pos = pos;
  }

  /** 
   * Determines if tile has been blocked.
   * @return if tile has been blocked.
   */
  public boolean isBlockedTile() {
    return blockedTile;
  }
  /** 
   * Sets whether the tile has been blocked or not.
   * @param openTile: true or false statement.
   */
  public void setBlockedTile(boolean openTile) {
    this.blockedTile = openTile;
  }

  /**
   * Get the list of neigboring tiles
   * @return list of neigboring tiles
   */
  public ArrayList<Tile> getNeigbourTiles() {
    return neigbourTiles;
  }

  /**
   * Change the list of neigbouring tiles
   * @param neigbourTiles: list of neigbouring tiles
   */
  public void setNeigbourTiles(ArrayList<Tile> neigbourTiles) {
    this.neigbourTiles = neigbourTiles;
  }

  /**
   * Determines if tile has been visited
   * @return if tile has been visited
   */
  public boolean isVisitedTile() {
    return visitedTile;
  }
  /**
   * Visit this tile
   */
  public void visitTile() {
    this.visitedTile = true;
  }
  
  /**
   * Unvisits this tile.
   */
  public void unVisitTile() {
    this.visitedTile = false;
  }
  
}
