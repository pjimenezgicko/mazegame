import java.awt.Color;

import javalib.worldimages.LineImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.RectangleImage;
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
  
  public WorldImage drawAt(WorldImage background, MazeWorld world) {
    return new LineImage(new Posn(this.to.xy.x, this.to.xy.y), Color.BLACK); 
  }
}