import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;


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