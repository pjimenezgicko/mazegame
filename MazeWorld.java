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
  
  MazeWorld() {
    
  }
  
  // 
  void initEmptyMaze() {
    this.maze = new ArrayList<ArrayList<Node>>(MAZE_SIZE);
    for (int i = 0; i < MAZE_SIZE; i++) {
      ArrayList<Node> temp = new ArrayList<Node>(MAZE_SIZE);
      for (int j = 0; j < MAZE_SIZE; j++) {
        temp.set(j, new Node(new Posn(i, j));
      }
      this.maze.set(i, temp);
    }
    this.initEdges();
  }
  
  
  void initEdges() {
    
  }
  
  void initHash() {
    
  }
}