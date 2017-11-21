import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeWorld extends World{
  // Size of maze
  static final int           MAZE_SIZE = 64;

  // Size of node
  static final int           NODE_SIZE = 10;

  // Maze represented as a 2D Arraylist of Nodes
  ArrayList<ArrayList<Node>> maze;
  
  // Maze as a 1D Arraylist
  ArrayList<Node> maze2;

  ArrayList<ArrayList<Edge>> edges;

  HashMap<Posn, Node>        hash;

  // image for the game
  WorldImage                 image     = new EmptyImage();
  // temp for testing
  WorldScene                 scene     = new WorldScene(MAZE_SIZE * 100, MAZE_SIZE * 100);

  MazeWorld() {

  }

  public WorldScene makeScene() {
    WorldScene w = new WorldScene(MazeWorld.MAZE_SIZE * 100, MazeWorld.MAZE_SIZE * 100);
    
    // Render the Cells
    for (Node n : this.maze2) {
      w.placeImageXY(n.drawAt(this.image), n.x * MazeWorld.NODE_SIZE, n.y * MazeWorld.NODE_SIZE);
    }

    return w;
  }

  // Init empty maze arraylist
  void initEmptyMaze() {
    this.maze = new ArrayList<ArrayList<Node>>(0);
    for (int i = 0; i < MazeWorld.MAZE_SIZE; i++) {
      ArrayList<Node> temp = new ArrayList<Node>(0);
      for (int j = 0; j < MazeWorld.MAZE_SIZE; j++) {
        temp.add(new Node(new Posn(i, j)));
      }
      this.maze.add(temp);
    }
    this.initEdges();
  }

  void initEdges() {

  }

  // Init hash table
  void initHash() {
    ArrayList<Node> tempMaze = new ArrayList<Node>(0);
    HashMap<Posn, Node> tempHash = new HashMap<Posn, Node>(0);
    for (ArrayList<Node> arr : this.maze) {
      for (Node n : arr) {
        tempMaze.add(n);
        tempHash.put(n.xy, n);
      }
    }
    
    this.maze2 = tempMaze;
    this.hash = tempHash;
  }
}