import java.awt.Color;

import javalib.worldimages.LineImage;
import javalib.worldimages.WorldImage;

class Edge {
  int weight;
  Node to;
  Node from;

  Edge(int weight, Node to, Node from) {
    this.weight = weight;
    this.to = to;
    this.from = from;
  }

  // Draw the line image
  public WorldImage drawAt(WorldImage background, MazeWorld world) {
    return new LineImage(this.from.xy, Color.BLACK);
  }
  
  // does this edge connect the given nodes? 
  public boolean connects(Node node1, Node node2) {
    return this.to.equals(node1) && this.from.equals(node2) ||
        this.to.equals(node2) && this.from.equals(node1);
  }
}