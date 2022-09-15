/**
 * Used to represent walls, other tiles such as pathfind can't
 * go through this tile.
 * @author ajith
 */
public class WallTile extends Tile {

  /**
   * Generates a Wall Tile
   * @param pos: Position of Wall tile.
   */
  public WallTile( Position pos) {
    super("W", pos, true);
  }

}
