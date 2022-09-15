import java.util.Objects;
/**
 * Used to determine each tiles position on the board
 * @author ajith
 *
 */
public class Position {
  private int x;
  private int y;
  
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Position other = (Position) obj;
    return x == other.x && y == other.y;
  }

  /**
   * Generates position instance
   * @param x: Value of X
   * @param y: Value of y
   */
  public Position(int x,int y) {    
    this.setX(x);
    this.setY(y);
  }

  @Override
  public String toString() {
    return "(" + this.x +","+ this.y +")";
  }
  
  /**
   * Gets the Y value position
   * @return the Y value
   */
  public int getY() {
    return y;
  }

  /**
   * Set the y value position
   * @param y: Y value
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Gets the X value position
   * @return the X value
   */
  public int getX() {
    return x;
  }

  /**
   * Set the x value position
   * @param x: X value
   */
  public void setX(int x) {
    this.x = x;
  }
  
  /**
   * Determines if the position is valid on the gameBoard
   * @param upperBound The max size of gameboard
   * @return true if position is valid, else false
   */
  public boolean validPositionSquare( int upperBound) {
    if(this.getX()>=0 && this.getX() <= upperBound && this.getY()>=0 && this.getY() <= upperBound) {
      return true;
    }
    return false;
  }

}
