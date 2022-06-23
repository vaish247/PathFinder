import java.util.ArrayList;
import java.util.Objects;

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
  
  
  private String id;
  private Position pos;
  private boolean blockedTile;
  private boolean visitedTile;
  private ArrayList<Tile> neigbourTiles;

  
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
  
  
  public double distanceCalc(Tile targetNode) {
    assert (this.getPos() == null);
    assert(targetNode != null);
    int x = getPos().getX()- targetNode.getPos().getX();
    int y = getPos().getY()- targetNode.getPos().getY();
    double x2 = Math.pow(x, 2);   
    double y2 = Math.pow(y, 2);
    return (double) Math.sqrt(x2+y2);
  }
  
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Position getPos() {
    return pos;
  }

  public void setPos(Position pos) {
    this.pos = pos;
  }

  public boolean isBlockedTile() {
    return blockedTile;
  }

  public void setBlockedTile(boolean openTile) {
    this.blockedTile = openTile;
  }

  public ArrayList<Tile> getNeigbourTiles() {
    return neigbourTiles;
  }

  public void setNeigbourTiles(ArrayList<Tile> neigbourTiles) {
    this.neigbourTiles = neigbourTiles;
  }

  public boolean isVisitedTile() {
    return visitedTile;
  }
  
  public void visitTile() {
    this.visitedTile = true;
  }
  public void unVisitTile() {
    this.visitedTile = false;
  }
  
}
