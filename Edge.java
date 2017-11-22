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
}