import java.util.*;
import java.awt.Color;
import javalib.worldimages.*;

class Node {

  ArrayList<Edge> outEdges;
  Posn xy;
  int x;
  int y;
  boolean visited;
  boolean fullPath = false;
  boolean directPath = false;
  Node parent; // Only used for breadth first search
  
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
  
  // Same as connect but given weight, not random
  public Edge connectTest(Node to, int weight) {
    Edge e1 = new Edge(weight, to, this);
    Edge e2 = new Edge(weight, this, to);
    this.outEdges.add(e1);
    to.outEdges.add(e2);
    return e1;
  }
  
  // does this node have an out edge to the given node? 
  public boolean hasEdge(Node to) {
    for (Edge e : this.outEdges) {
      if (e.connects(this,to)) {
        return true;
      }
    }
    return false;
  }

  // create image of tile
  public WorldImage drawAt(WorldImage background, MazeWorld world) {
    return new OverlayOffsetImage(
        new RectangleImage(MazeWorld.NODE_SIZE,
            MazeWorld.NODE_SIZE,
            OutlineMode.SOLID,
            this.getColor(world)),
        -2.5 * MazeWorld.NODE_SIZE + world.width,
        -2.5 * MazeWorld.NODE_SIZE + world.height,
        background);
  }

  // get color of nodes based on location
  public Color getColor(MazeWorld world) {
    // top left
    if (x == 0 && y == 0) {
      return Color.GREEN;
    }
    
    // bottom right
    else if (x == world.height - 1 && y == world.width - 1) {
      return Color.MAGENTA;
    }
    
    // has been visited by the player 
    else if (this.visited) {
      return Color.RED;
    }
    
    // part of direct path
    else if (this.directPath) {
      return Color.BLUE;
    }
    
    // part of full path
    else if (this.fullPath) {
      return Color.CYAN;
    }
    // everywhere else
    else {
      return Color.LIGHT_GRAY;
    }
  }
  
  public void setParent(Node node) {
    this.parent = node;
  }
  
  
  //--------------------- PART 2 ---------------------------
  
  
}
