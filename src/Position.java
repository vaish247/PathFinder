import java.util.Objects;

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

  public Position(int x,int y) {    
    this.setX(x);
    this.setY(y);
  }

  @Override
  public String toString() {
    return "(" + this.x +","+ this.y +")";
  }
  
  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }
  
  public boolean validPositionSquare( int upperBound) {
    if(this.getX()>=0 && this.getX() <= upperBound && this.getY()>=0 && this.getY() <= upperBound) {
      return true;
    }
    return false;
  }

}
