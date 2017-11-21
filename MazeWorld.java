import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeWorld extends World{

  // Size of the maze 
  int height;
  
  int width;

  // Size of node
  static final int NODE_SIZE = 25;
  
  // Range of our random numbers
  static final int RAND_RANGE = 20;

  // Maze represented as a 2D Arraylist of Nodes
  ArrayList<ArrayList<Node>> maze;

  ArrayList<Edge> edges = new ArrayList<Edge>();

  HashMap<Posn, Node> hash;

  // Maze as a 1D Arraylist
  ArrayList<Node> maze2;

  // image for the game
  WorldImage image = new EmptyImage();
  // temp for testing
  WorldScene scene = new WorldScene(this.height * 100, this.width * 100);
  
  // allows for variable sized mazes for testing
  MazeWorld(int height, int width) {
    this.height = height;
    this.width = width;
  }

  public WorldScene makeScene() {
    WorldScene w = new WorldScene(this.height* 100, this.width * 100);
    int node = MazeWorld.NODE_SIZE;
    // Render the Cells
    for (Node n : this.maze2) {
      w.placeImageXY(n.drawAt(this.image,this), n.x * node, n.y * node);
      w.placeImageXY(new CircleImage(1,OutlineMode.SOLID, Color.RED), n.x * node, n.y * node);
    }
    for (Edge e : this.edges) {
      w.placeImageXY(new LineImage(new Posn(node, 0), Color.BLACK), e.to.x * node, e.to.y * node);
      w.placeImageXY(new LineImage(new Posn(0, node),  Color.BLACK), e.to.x * node, e.to.y * node);
    }

    return w;
  }

  // Init empty maze arraylist
  void initEmptyMaze() {
    this.maze = new ArrayList<ArrayList<Node>>(this.height);
    for (int i = 0; i < this.height; i++) {
      ArrayList<Node> temp = new ArrayList<Node>(this.width);
      for (int j = 0; j < this.width; j++) {
        temp.add(j, new Node(new Posn(i, j)));
      }
      this.maze.add(i, temp);
    }
    this.initEdges();
    this.initHash();
  }

  // EFFECT: Create edges for the maze
  void initEdges() {
    Utils<Node> u = new Utils<Node>();
    // Edges for the top left corner
    this.edges.add(u.getValue(this.maze, new Posn(0, 0)).connect(u.getValue(this.maze, new Posn(1, 0))));
    this.edges.add(u.getValue(this.maze, new Posn(0, 0)).connect(u.getValue(this.maze, new Posn(0, 1))));

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
        Posn p = new Posn(i,j);
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, j-1)))); // top
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, j+1)))); // bottom
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i + 1, j)))); // right 
        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i -1, j)))); // left
      }
    }

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
  
  void KruskalsAlg() {
    HashMap<Posn, Node> base = this.hash;
    ArrayList<Edge> edges = this.edges;
    ArrayList<Edge> span = new ArrayList<Edge>(0);
    edges.sort(new SortWeight());
    boolean isSpan = false;
    
    Edge temp = edges.get(0);
    span.add(temp);
    base.put(temp.to.xy, temp.from);
    edges.remove(0);
    /*
    HashMap<String, String> representatives;
    List<Edge> edgesInTree;
    List<Edge> worklist = all edges in graph, sorted by edge weights;
 
    initialize every node's representative to itself
    While(there's more than one tree)
      Pick the next cheapest edge of the graph: suppose it connects X and Y.
      If find(representatives, X) equals find(representatives, Y):
        discard this edge  // they're already connected
      Else:
        Record this edge in edgesInTree
        union(representatives,
            find(representatives, X),
            find(representatives, Y))
    Return the edgesInTree
    */
    Node a;
    Node b;
    
    Posn apos;
    Posn bpos;
    while(!isSpan) {
      temp = edges.get(0);
      a = temp.to;
      apos = a.xy;
      b = temp.from;
      bpos = b.xy;

      // Find shortest edge and check if it creates a cycle
      // If it doesn't create cycle, 
      if(!(base.get(apos).xy.equals((base.get(bpos).xy)))) {
        union(a, b, base); 
      }
      edges.remove(0);
      
      // Check if spanning tree is spanning
      if(span.size() == this.maze2.size() - 1) {
        isSpan = true;
      }
    }
    this.edges = span;
  }
  
  void union(Node a, Node b, HashMap<Posn, Node> base) {
    if(base.get(a.xy).xy.equals(a.xy)) {
      base.put(a.xy, b);
    }
    else {
      union(base.get(a.xy), b, base);
    }
  }
}

class SortWeight implements Comparator<Edge>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Edge a, Edge b)
    {
       return a.weight < b.weight ? -1 : a.weight == b.weight ? 0 : 1;
    }
}
 

class Utils<T> {

  // get the value at a Posn from a given 2D array
  T getValue(ArrayList<ArrayList<T>> matrix, Posn p) {
    return matrix.get(p.x).get(p.y);
  }

  // return a random int
  int random() {
    return (int)(Math.random() * MazeWorld.RAND_RANGE);
  }
}

class ExamplesMaze {
  MazeWorld ex1;
  
  // set up the test conditions for a 64x64 maze
  void initTest64() {
   ex1 = new MazeWorld(64, 64); 
   ex1.initEmptyMaze();
  }
  
  // set up the test conditions for a 3x3 maze
  void initTest3() {
    ex1 = new MazeWorld(3, 3);
    ex1.initEmptyMaze();
  }
  // test making the empty maze 
  void testInitEmptyMaze(Tester t) {
    this.initTest64();
    t.checkExpect(ex1.maze.size(), 64);
    t.checkExpect(ex1.maze.get(0).size(), 64);
    this.initTest3();
    t.checkExpect(ex1.maze.size(), 3);
    t.checkExpect(ex1.maze.get(0).size(), 3);
  }
  
  // test Edge Creation
  void testInitEdges(Tester t) {
    this.initTest64();
    t.checkExpect(ex1.edges.size(), 16128);
    this.initTest3();
    t.checkExpect(ex1.edges.size(), 24);
  }
  // test the getValue method 
  void testGetValue(Tester t) {
    this.initTest64();
    Utils<Integer> u = new Utils<Integer>();
    Posn p = new Posn(0,1);
    Posn p2 = new Posn(1,3);
    ArrayList<Integer> l1 = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
    ArrayList<Integer> l2 = new ArrayList<Integer>(Arrays.asList(5,6,7,8));
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(Arrays.asList(l1,l2));
    t.checkExpect(u.getValue(matrix, p), 2);
    t.checkExpect(u.getValue(matrix, p2), 8);
  }
  
  // test get random value in the utils class
  void testRandom(Tester t) {
    this.initTest3();
    Utils<Integer> u = new Utils<Integer>();
    t.checkNumRange(u.random(), 0, MazeWorld.RAND_RANGE);
  }
  
  // test the connect method in the Node class 
  void testConnect(Tester t) {
    Node n1 = new Node(new Posn(1,1));
    Node n2 = new Node(new Posn(0,0));
    t.checkExpect(n1.connect(n2).to, n2);
  }
  
  // test init hash 
  void testInitHash(Tester t) {
    this.initTest3();
    t.checkExpect(ex1.hash.get(ex1.maze2.get(0).xy), ex1.maze2.get(0));
    t.checkExpect(ex1.hash.get(ex1.maze2.get(6).xy), ex1.maze2.get(6));
  }
  
  // draw at 
  
  // test is Equal in the Posn class
  void testIsEqual(Tester t) {
    Posn p1 = new Posn(0,0); 
    Posn p2 = new Posn(1, 0); 
    Posn p3 = new Posn(1, 0);
    t.checkExpect(p1.equals(p2), false);
    t.checkExpect(p2.equals(p3), true);
  }
  
  // Test get Color 
  void testGetColor(Tester t) {
    this.initTest3();
    Node n = new Node(new Posn(0,0));
    t.checkExpect(n.getColor(ex1), Color.GREEN);
  }
  // Test the rendering 
  void testRender(Tester t) {
    ex1 = new MazeWorld(25, 25); 
    ex1.initEmptyMaze();
    ex1.bigBang(ex1.height * MazeWorld.NODE_SIZE,
        ex1.width * MazeWorld.NODE_SIZE, .5);
  }
}