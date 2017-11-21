import java.util.ArrayList;

class Node {
  
  ArrayList<Edge> outEdges;
  Posn posn;
  int x;
  int y;
  
  Node(Posn posn) {
    this.outEdges = new ArrayList<Edge>();
    this.posn = posn;
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

}