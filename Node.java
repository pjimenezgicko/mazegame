import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
class Node {
  
  ArrayList<Edge> outEdges;
  Posn xy;
  int x;
  int y;
  
  Node(Posn posn) {
    this.outEdges = new ArrayList<Edge>();
    this.xy = posn;
    this.x = xy.x;
    this.y = xy.y;
  }
  
  // Effect: update the edges of this node and the given node
  // return the edge that connects these two nodes
  public Edge connect(Node to) {
    Utils<Node> u = new Utils<Node>();
    Edge e = new Edge(u.random(), this, to);
    this.outEdges.add(e);
    to.outEdges.add(e);
    return e;
  }
  
  public WorldImage drawAt(WorldImage background) {
    return new OverlayImage(new RectangleImage(MazeWorld.MAZE_SIZE,
        MazeWorld.NODE_SIZE, OutlineMode.SOLID, this.getColor() ), background);
  }
  
  private Color getColor() {
    return Color.LIGHT_GRAY;
  }
}
