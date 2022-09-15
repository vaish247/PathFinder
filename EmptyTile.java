/**
 * Creates an empty tile
 * @author ajith
 *
 */
public class EmptyTile extends Tile{
  /**
   * Generates empty tile
   * @param pos: Unique positon of tile.
   */
  public EmptyTile( Position pos) {
    super("_", pos, false);
  }

  
}
