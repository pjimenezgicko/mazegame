import java.util.ArrayList;
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
    if (x == world.height - 1 && y == world.width - 1) {
      return Color.MAGENTA;
    } 
    // everywhere else
    else {
      return Color.LIGHT_GRAY;
    }
  }
}
