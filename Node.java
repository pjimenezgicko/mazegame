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
    int weight = u.random();
    Edge e1 = new Edge(weight, to, this);
    Edge e2 = new Edge(weight, this, to);
    this.outEdges.add(e1);
    to.outEdges.add(e2);
    return e1;
  }
  
  public WorldImage drawAt(WorldImage background, MazeWorld world) {
    return new OverlayImage(new RectangleImage(world.size,
        MazeWorld.NODE_SIZE, OutlineMode.SOLID, this.getColor() ), background);
  }
  
  public Color getColor() {
    return Color.LIGHT_GRAY;
  }
}
