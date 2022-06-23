import java.util.ArrayList;

public class ASNodes implements Comparable<ASNodes> {
  
  private Tile currentTile;
  private Tile prevTile;
  private Tile targetTile;
  private double gTileDist;
  private double hTileDist;
  private double fTileDist;

//  private ArrayList<>

  public double getgTileDist() {
    return gTileDist;
  }

  public void setgTileDist(double gTileDist) {
    this.gTileDist = this.gTileDist+ gTileDist;
  }

  public double gethTileDist() {
    return hTileDist;
  }

  public void sethTileDist(double hTileDist) {
    this.hTileDist = hTileDist;
  }

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

  public Tile getCurrentTile() {
    return currentTile;
  }

  public void setCurrentTile(Tile currentTile) {
    this.currentTile = currentTile;
  }

  @Override
  public int compareTo(ASNodes o) {
    // TODO Auto-generated method stub
    if(this.getfTileDist()> o.getfTileDist()) {
      return 1;
    }
    if(this.getfTileDist()< o.getfTileDist()) {
      return -1;
    }
    return 0;
  }

  public Tile getPrevTile() {
    return prevTile;
  }

  public void setPrevTile(Tile prevTile) {
    this.prevTile = prevTile;
  }

  public Tile getTargetTile() {
    return targetTile;
  }

  public void setTargetTile(Tile targetTile) {
    this.targetTile = targetTile;
  }

  public double getfTileDist() {
    return fTileDist;
  }

  public void setfTileDist() {
    this.fTileDist = this.gTileDist+ this.hTileDist;
  }


}
