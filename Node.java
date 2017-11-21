import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Node {
  Posn xy;
  
  int x;
  int y;
  ArrayList<Edge> out;
  
  
  Node(Posn xy) {
    this.xy = xy;
    this.x = xy.x;
    this.y = xy.y;
    this.out = new ArrayList<Edge>(0);
  }
  
  Node(Posn xy, ArrayList<Edge> out) {
    this.xy = xy;
    this.x = xy.x;
    this.y = xy.y;
    this.out = out;
  }
}