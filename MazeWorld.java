import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeWorld {
  // Size of maze
  static final int MAZE_SIZE = 64;

  // Size of node
  static final int NODE_SIZE = 10;

  // Maze represented as a 2D Arraylist of Nodes
  ArrayList<ArrayList<Node>> maze;

  ArrayList<Edge> edges;

  HashMap<Posn, Node> hash;

  // Maze as a 1D Arraylist
  ArrayList<Node> maze2;

  // image for the game
  WorldImage image = new EmptyImage();
  // temp for testing
  WorldScene scene = new WorldScene(MAZE_SIZE * 100, MAZE_SIZE * 100);

  MazeWorld() {

  }

  public WorldScene makeScene() {
    WorldScene w = new WorldScene(this.MAZE_SIZE * 100, MazeWorld.MAZE_SIZE);

    // Render the Cells
    for (Node n : this.maze2) {
      w.placeImageXY(n.drawAt(this.image), n.x * MazeWorld.NODE_SIZE, n.y * MazeWorld.NODE_SIZE);
    }

    return w;
  }

  //
  void initEmptyMaze() {
    this.maze = new ArrayList<ArrayList<Node>>(MAZE_SIZE);
    for (int i = 0; i < MAZE_SIZE; i++) {
      ArrayList<Node> temp = new ArrayList<Node>(MAZE_SIZE);
      for (int j = 0; j < MAZE_SIZE; j++) {
        temp.add(j, new Node(new Posn(i, j)));
      }
      this.maze.add(i, temp);
    }
    //this.initEdges();
  }

  // EFFECT: Create edges for the maze
  void initEdges() {
    Utils<Node> u = new Utils<Node>();
    // Edges for the top left corner
    this.edges
        .add(u.getValue(this.maze, new Posn(0, 0)).connect(u.getValue(this.maze, new Posn(1, 0))));
    this.edges
        .add(u.getValue(this.maze, new Posn(0, 0)).connect(u.getValue(this.maze, new Posn(0, 1))));

    // Edges for the top right corner
    int rightindex = this.maze.size() - 1;
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, 0))
        .connect(u.getValue(this.maze, new Posn(rightindex - 1, 0))));
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, 0))
        .connect(u.getValue(this.maze, new Posn(rightindex, 1))));

    // Edges for bottom left corner
    int bottomindex = this.maze.get(this.maze.size() - 1).size() - 1;
    this.edges.add(u.getValue(this.maze, new Posn(0, bottomindex))
        .connect(u.getValue(this.maze, new Posn(1, bottomindex))));
    this.edges.add(u.getValue(this.maze, new Posn(0, bottomindex))
        .connect(u.getValue(this.maze, new Posn(0, bottomindex - 1))));

    // Edges for the bottom right corner
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, bottomindex))
        .connect(u.getValue(this.maze, new Posn(rightindex - 1, bottomindex))));
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, bottomindex))
        .connect(u.getValue(this.maze, new Posn(rightindex, bottomindex - 1))));

    // Edges for the left border
    for (int j = 1; j < this.maze.get(0).size() - 1; j++) {
      Posn p = new Posn(0, j);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(0, j - 1)))); // top
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(0, j + 1)))); // bottom
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(1, j)))); // right
    }

    // Edges for the right border
    for (int j = 1; j < this.maze.get(this.maze.size() - 1).size() - 1; j++) {
      Posn p = new Posn(rightindex, j);
      this.edges.add(
          u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex, j - 1)))); // top
      this.edges.add(
          u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex, j + 1)))); // bottom
      this.edges.add(
          u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex - 1, j)))); // left
    }

    // Edges for the top border
    for (int i = 1; i < this.maze.get(this.maze.size() - 1).size() - 1; i++) {
      Posn p = new Posn(i, 0);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i + 1, 0)))); // right
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i - 1, 0)))); // left
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, 1)))); // bottom
    }

    // Edges for the bottom border
    for (int i = 1; i < this.maze.get(this.maze.size() - 1).size() - 1; i++) {
      Posn p = new Posn(i, bottomindex);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i + 1, bottomindex)))); // right
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i - 1, bottomindex)))); // left 
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, bottomindex - 1)))); // top 
    }

    // Edges for the rest of the array
    for (int i = 1; i < this.maze.size() - 1; i++) {
      for (int j = 1; j < this.maze.get(0).size() - 1; j++) {
        Node center = this.maze.get(i).get(j);
        Posn p = new Posn(i,j);
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, j-1)))); // top
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, j+1)))); // bottom
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i + 1, j)))); // right 
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i -1, j)))); // left
      }
    }

  }

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

class Utils<T> {

  // get the value at a Posn from a given 2D array
  T getValue(ArrayList<ArrayList<T>> matrix, Posn p) {
    return matrix.get(p.x).get(p.y);
  }

  // return a random int
  int random() {
    return (int) Math.random() * 100;
  }
}

class ExamplesMaze {
  MazeWorld ex1 = new MazeWorld();
  // test Edge creation 
  void testInitEdges(Tester t) {
    ex1.initEmptyMaze();
    t.checkExpect(ex1.maze.size(), 64);
    t.checkExpect(ex1.maze.get(0).size(), 64);
  }
  
  // test the getValue method 
  void testGetValue(Tester t) {
    Utils<Integer> u = new Utils<Integer>();
    Posn p = new Posn(0,1);
    Posn p2 = new Posn(1,3);
    ArrayList<Integer> l1 = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
    ArrayList<Integer> l2 = new ArrayList<Integer>(Arrays.asList(5,6,7,8));
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(Arrays.asList(l1,l2));
    t.checkExpect(u.getValue(matrix, p), 2);
    t.checkExpect(u.getValue(matrix, p2), 8);
  }

}