import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
class Edge {
  int weight;
  Node to;
  Node from;
  
  Edge(int weight, Node to, Node from) {
    this.weight = weight;
    this.to = to;
    this.from = from;
  }
  
  
}