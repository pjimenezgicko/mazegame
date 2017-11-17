import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeWorld {
  // Size of maze
  static final int MAZE_SIZE = 64;
  
  // Maze represented as a 2D Arraylist of Nodes
  ArrayList<ArrayList<Node>> maze;
  
  ArrayList<ArrayList<Edge>> edges;
  
  HashMap<Posn, Node> hash;
  
  MazeWorld()
}