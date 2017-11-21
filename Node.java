import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Node {
  
  ArrayList<Edge> outEdges;
  Posn posn;
  
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