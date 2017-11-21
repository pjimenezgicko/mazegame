
class Edge {
  int weight;
  Node to;
  Node from;
  
  Edge(int weight, Node to, Node from) {
    this.weight = weight;
    this.to = to;
    this.from = from;
  }
  
  /*public WorldImage drawAt(WorldImage background) {
    return new OverlayImage(new RectangleImage(ForbiddenIslandWorld.CELL_SIZE,
        ForbiddenIslandWorld.CELL_SIZE, OutlineMode.SOLID, this.getColor()), background); 
  }*/
}