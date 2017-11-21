
class Posn {
  int x;
  int y;
  
  Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public boolean isEqual(Posn p) {
    return (p.x == this.x && p.y == this.y);
  }
}