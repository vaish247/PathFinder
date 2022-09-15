import java.util.ArrayList;
/**
 * A star nodes used to determine the shortest path
 * @author ajith
 *
 */

public class ASNodes implements Comparable<ASNodes> {
  
  /**
   * Current tile used in the node
   */
  private Tile currentTile;
  /**
   * Previous tile used in the node
   */
  private Tile prevTile;
  /**
   * Target tile used in the algorithm
   */
  private Tile targetTile;
  /**
   * the movement cost to move from the starting point to 
   * a given square on the board,
   */
  private double gTileDist;
  /**
   *  the estimated movement cost to move from that given square on the grid to 
   *  the final destination
   */
  private double hTileDist;
  /**
   * Value that is the sum of hTiledist and gTiledist
   */
  private double fTileDist;

  /**
   * get g value;
   * @return g value
   */
  public double getgTileDist() {
    return gTileDist;
  }

  /**
   * set the g value
   * @param gTileDist: g value
   */
  public void setgTileDist(double gTileDist) {
    this.gTileDist = this.gTileDist+ gTileDist;
  }

  /**
   * get the h value;
   * @return h value
   */
  public double gethTileDist() {
    return hTileDist;
  }

  /**
   * set the h value
   * @param hTileDist: h value
   */
  public void sethTileDist(double hTileDist) {
    this.hTileDist = hTileDist;
  }

  /**
   * Generates the A star nodes
   * @param currentTile
   * @param prevTile
   * @param targetTile
   */
  public ASNodes(Tile currentTile, Tile prevTile, Tile targetTile) {
    setCurrentTile(currentTile);
    setPrevTile(prevTile);
    if(getPrevTile() == null) {
      this.gTileDist = 0;
    }else {
      this.gTileDist = currentTile.distanceCalc(prevTile);
      }
    setTargetTile(targetTile);
    this.hTileDist = currentTile.distanceCalc(targetTile);
  }

  /**
   * Gets the current tile
   * @return the current tile
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Sets the current tile
   * @return the current tile
   */
  public void setCurrentTile(Tile currentTile) {
    this.currentTile = currentTile;
  }

  @Override
  public int compareTo(ASNodes o) {
    if(this.getfTileDist()> o.getfTileDist()) {
      return 1;
    }
    if(this.getfTileDist()< o.getfTileDist()) {
      return -1;
    }
    return 0;
  }

  /**
   * Gets the previous tile
   * @return the previous tile
   */
  public Tile getPrevTile() {
    return prevTile;
  }
  /**
   * Sets the previous tile
   */
  public void setPrevTile(Tile prevTile) {
    this.prevTile = prevTile;
  }

  /**
   * Gets the target tile
   * @return the target tile
   */
  public Tile getTargetTile() {
    return targetTile;
  }

  /**
   * Sets the target tile
   */
  public void setTargetTile(Tile targetTile) {
    this.targetTile = targetTile;
  }

  /**
   * Gets the f value
   * @return the f value
   */
  public double getfTileDist() {
    return fTileDist;
  }

  /**
   * Sets the f value
   */
  public void setfTileDist() {
    this.fTileDist = this.gTileDist+ this.hTileDist;
  }


}
