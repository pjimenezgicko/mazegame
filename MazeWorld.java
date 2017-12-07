import java.util.*;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeWorld extends World {

  // Size of the maze
  int nodesTall;
  int nodesWide;
  int nodeSize;

  // Default size of the window
  static final int WIDTH = 1200;
  static final int HEIGHT = 800;

  // Size of node
  static final int NODE_SIZE = 10;

  // Range of our random numbers
  static final int RAND_RANGE = 25;

  // Scaling factor of our maze
  static final int SCALE = 25;

  // Maze represented as a 2D Arraylist of Nodes
  ArrayList<ArrayList<Node>> maze;

  ArrayList<Edge> edges = new ArrayList<Edge>();

  ArrayList<Edge> walls = new ArrayList<Edge>();

  HashMap<Posn, Node> hash;

  // Maze as a 1D Arraylist
  ArrayList<Node> maze2;

  // image for the game
  WorldImage image = new EmptyImage();
  // temp for testing
  WorldScene scene = new WorldScene(this.nodesWide * SCALE, this.nodesTall * SCALE);

  // represent the Player
  Player player;

  // Starting node
  Node start;

  // Ending node
  Node end;

  ArrayList<Node> depthPath = new ArrayList<Node>(0);
  int dp = -1;
  ArrayList<Node> depthPathFull = new ArrayList<Node>(0);
  int dpf = -1;
  ArrayList<Node> breadthPath = new ArrayList<Node>(0);
  int bp = -1;
  ArrayList<Node> breadthPathFull = new ArrayList<Node>(0);
  int bpf = -1;

  // For timing the solving of the maze animation
  boolean solveBreadth = false;
  boolean solveDepth = false;

  // allows for variable sized mazes for testing
  MazeWorld(int nodesWide, int nodesTall) {
    this.nodesWide = nodesWide;
    this.nodesTall = nodesTall;

  }

  public WorldScene makeScene() {
    WorldScene w = new WorldScene(this.nodesWide * SCALE, this.nodesTall * SCALE);
    int node = MazeWorld.NODE_SIZE;
    // Render the Cells
    for (Node n : this.maze2) {
      w.placeImageXY(n.drawAt(this.image, this), n.x * node + node / 2, n.y * node + node / 2);
    }

    // draw the top border
    w.placeImageXY(new LineImage(new Posn(node * this.nodesWide, 0), Color.BLACK)
        .movePinhole(-(node * this.nodesWide) / 2, 0), 0, 0);

    // draw the left border
    w.placeImageXY(new LineImage(new Posn(0, -node * this.nodesTall), Color.BLACK).movePinhole(0,
        -(node * this.nodesTall) / 2 + 1), 0, 0);

    // draw the bottom border
    w.placeImageXY(new LineImage(new Posn(node * this.nodesWide, 0), Color.BLACK)
        .movePinhole(-(node * this.nodesWide) / 2, -(node * this.nodesTall) + 1), 0, 0);

    // draw the right border
    w.placeImageXY(new LineImage(new Posn(0, -node * this.nodesTall), Color.BLACK)
        .movePinhole(-(node * this.nodesWide) + 1, -(node * this.nodesTall) / 2 + 1), 0, 0);

    // draw the inner walls
    for (Edge e : this.edges) {
      if (e.to.x < e.from.x) {
        w.placeImageXY(new LineImage(new Posn(0, node), Color.BLACK).movePinhole(-node, -node),
            e.to.x * node, e.to.y * node - node / 2); // side
      }
      if (e.to.y > e.from.y) {
        w.placeImageXY(new LineImage(new Posn(node, 0), Color.BLACK).movePinhole(0, 0),
            e.to.x * node + node / 2, e.to.y * node); // bottom
      }
    }

    // draw the player
    w.placeImageXY(this.player.drawAt(this.image), this.player.x * node + node / 2 + 1,
        this.player.y * node + node / 2 + 1);
    return w;
  }

  // Handle the key presses of the player
  // EFFECT: move the player character and restart the game if needed
  public void onKeyEvent(String key) {
    // handle player movement using the arrow keys
    if (key.equals("up") || key.equals("down") || key.equals("left") || key.equals("right")) {
      this.player = this.player.movePlayer(key, this.maze2, this.walls);
    }

    if (key.equals("b")) {
      this.bpf = 0;
    }

    if (key.equals("d")) {
      this.dpf = 0;
    }
  }

  public void onTick() {
    // if the player is on the end node of the maze they win
    if (this.player.x == this.nodesWide - 1 && this.player.y == this.nodesTall - 1) {
      this.endOfWorld("YOU WIN!!!");
    }

    if (this.bpf != -1) {
      this.breadthPathFull.get(bpf).fullPath = true;
      this.bpf++;

      if (this.bpf == this.breadthPathFull.size()) {
        this.bpf = -1;
        this.bp = 0;
      }
    }

    else if (this.bp != -1) {
      this.breadthPath.get(bp).directPath = true;
      this.bp++;
      if (this.bp == this.breadthPath.size()) {
        this.bp = -1;
      }
    }

    if (this.dpf != -1) {
      this.depthPathFull.get(dpf).fullPath = true;
      this.dpf++;
      if (this.dpf == this.depthPathFull.size()) {
        this.dpf = -1;
        this.dp = 0;
      }
    }

    else if (this.dp != -1) {
      this.depthPath.get(dp).directPath = true;
      this.dp++;
      if (this.dp == this.depthPath.size()) {
        this.dp = -1;
      }
    }
  }

  // Last Scene when the player wins!
  public WorldScene lastScene(String msg) {
    WorldScene w = new WorldScene(this.nodesWide * SCALE, this.nodesTall * SCALE);
    w.placeImageXY(new TextImage(msg, Color.RED), this.nodesWide / 2 * MazeWorld.NODE_SIZE,
        this.nodesTall / 2 * MazeWorld.NODE_SIZE);
    return w;
  }

  // Init empty maze arraylist
  void initEmptyMaze() {
    this.maze = new ArrayList<ArrayList<Node>>(this.nodesWide);
    for (int i = 0; i < this.nodesWide; i++) {
      ArrayList<Node> temp = new ArrayList<Node>(this.nodesTall);
      for (int j = 0; j < this.nodesTall; j++) {
        temp.add(j, new Node(new Posn(i, j)));
      }
      this.maze.add(i, temp);
    }
    System.out.println("wide -" + this.maze.size());
    System.out.println("tall - " + this.maze.get(0).size());
    this.initEdges();
    this.initHash();
  }

  // EFFECT: Create edges for the maze
  void initEdges() {
    Utils<Node> u = new Utils<Node>();
    int rightindex = this.maze.size() - 1;
    int bottomindex = this.maze.get(rightindex).size() - 1;

    // Edges for the top left corner
    this.edges
        .add(u.getValue(this.maze, new Posn(0, 0)).connect(u.getValue(this.maze, new Posn(0, 1))));

    // Edges for the top right corner
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, 0))
        .connect(u.getValue(this.maze, new Posn(rightindex - 1, 0))));
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, 0))
        .connect(u.getValue(this.maze, new Posn(rightindex, 1))));

    // Edges for the bottom right corner
    this.edges.add(u.getValue(this.maze, new Posn(rightindex, bottomindex))
        .connect(u.getValue(this.maze, new Posn(rightindex - 1, bottomindex))));

    // Edges for the left border
    for (int j = 1; j < this.maze.get(0).size() - 1; j++) {
      Posn p = new Posn(0, j);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(0, j + 1))));
      // bottom

    }

    // Edges for the right border
    for (int j = 1; j < this.maze.get(this.maze.size() - 1).size() - 1; j++) {
      Posn p = new Posn(rightindex, j);
      this.edges.add(
          u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex, j + 1))));
      // bottom
      this.edges.add(
          u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex - 1, j))));
      // left
    }

    // Edges for the top border
    for (int i = 1; i < this.maze.size() - 1; i++) {
      Posn p = new Posn(i, 0);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i - 1, 0)))); // left
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, 1)))); // bottom
    }

    // Edges for the bottom border
    for (int i = 1; i < this.maze.size() - 1; i++) {
      Posn p = new Posn(i, bottomindex);
      this.edges.add(
          u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i - 1, bottomindex))));
    }

    // Edges for the rest of the array
    for (int i = 1; i < this.maze.size() - 1; i++) {
      for (int j = 1; j < this.maze.get(0).size() - 1; j++) {
        Posn p = new Posn(i, j);

        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i, j + 1))));
        // bottom

        this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i - 1, j)))); // left
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
    this.start = this.maze2.get(0);
    this.end = this.maze2.get(this.maze2.size() - 1);
    this.hash = tempHash;
    this.player = new Player(this.start);
  }

  // Kruskal's algorithm
  // PS i keep track of everything but the spanning tree so i dont have to invert
  // later
  void kruskalsAlg() {
    HashMap<Node, Node> base = new HashMap<Node, Node>();
    for (Node n : this.maze2) {
      base.put(n, n);
    }
    ArrayList<Edge> antispan = new ArrayList<Edge>(0);
    // Sort edges
    this.edges.sort(new SortWeight());

    // Get first edge and put it in hashtable
    Edge temp = this.edges.get(0);
    base.put(baseRep(temp.to, base), baseRep(temp.from, base));

    // Initilize locals
    Node a;
    Node b;
    int i = 1;
    boolean isSpan = false;

    while (!isSpan) {
      // Get current edge from edges
      temp = this.edges.get(i);
      a = temp.to;
      b = temp.from;

      // Find shortest edge and check if it creates a cycle
      // If it doesn't create cycle, modify hash table
      if (baseRep(a, base) != (baseRep(b, base))) {
        base.put(baseRep(a, base), baseRep(b, base));
      }
      else {
        // Add edge to spanning tree
        antispan.add(temp);
      }

      // tick counter
      i++;

      // Check if everything but antispanning tree is the required size
      if (this.edges.size() - antispan.size() == this.maze2.size() - 1) {
        isSpan = true;
      }
    }

    IPred<Edge> p = new CompareLink();
    // also get the inverse of the edges
    // Ex: if we have A -> B make sure we have B -> A
    for (Edge e1 : this.edges) {
      for (Edge e2 : antispan) {
        if (p.apply(e1, e2)) {
          this.walls.add(e1);
          this.walls.add(e2);
        }
      }
    }
    // set edges to the antispanning tree
    this.edges = antispan;

    // remove the edges where walls are
    for (Node n : this.maze2) {
      n.outEdges.removeAll(this.walls);
    }

  }

  // same as find function for kruskals
  Node baseRep(Node a, HashMap<Node, Node> base) {
    if (base.get(a) == a) {
      return a;
    }
    else {
      return baseRep(base.get(a), base);
    }
  }

  void breadthSearch(Node root) {
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(root);
    unmarkNodes(this.maze2);
    while (!queue.isEmpty()) {
      System.out.println(queue.size() + " b4 pop");
      Node next = queue.remove();
      if (next.marked) {
        System.out.println("its marked, ignore");
      }
      else {
        next.marked = true;
        this.breadthPathFull.add(next);
        if (next == this.end) {
          System.out.println("oh yeah");
          recreatePath(this.breadthPath);
          break;
        }
        else {
          for (Edge e : next.outEdges) {
            System.out.println(queue.size() + " b4 push");
            if (!e.to.marked) {
              queue.add(e.to);
              e.to.parent = next;
            }
          }
        }
      }
    }
  }

  void recreatePath(ArrayList<Node> destination) {
    ArrayList<Node> arr = new ArrayList<Node>(0);
    Node node = this.end;
    while (node.parent != null) {
      arr.add(node);
      System.out.println(node.parent == null);
      node = node.parent;
    }

    if (destination.equals(this.depthPath)) {
      System.out.println(arr.size());
      System.out.println(destination.size());
      System.out.println(depthPath.size());
      System.out.println(depthPathFull.size());
      this.depthPath = arr;
      System.out.println(destination.size());
      System.out.println(depthPath.size());
    }
    else {
      System.out.println(arr.size());
      System.out.println(destination.size());
      System.out.println(breadthPath.size());
      System.out.println(breadthPathFull.size());
      this.breadthPath = arr;
      System.out.println(destination.size());
      System.out.println(breadthPath.size());
    }
  }

  void depthSearch(Node root) {
    Stack<Node> stack = new Stack<Node>();
    stack.push(root);
    unmarkNodes(this.maze2);
    while (!stack.isEmpty()) {
      System.out.println(stack.size() + " b4 pop");
      Node next = stack.pop();
      if (next.marked) {
        System.out.println("its marked, ignore");
      }
      else {
        next.marked = true;
        this.depthPathFull.add(next);
        if (next == this.end) {
          System.out.println("oh yeah");
          recreatePath(this.depthPath);
          break;
        }
        else {
          for (Edge e : next.outEdges) {
            System.out.println(stack.size() + " b4 push");
            if (!e.to.marked) {
              stack.push(e.to);
              e.to.parent = next;
            }
          }
        }
      }
    }
  }

  void fixOutEdges() {
    for (int i = 0; i < this.maze2.size(); i++) {
      Node n = this.maze2.get(i);
      Node up;
      Node right;
      if (n.y == 0 && n.x == this.nodesWide - 1) {
        System.out.println("top right");
      }
      else if (n.y == 0) {
        System.out.println("top");
        right = this.maze2.get(i + this.nodesTall);
        ArrayList<Node> rightNeighbors = new ArrayList<Node>(0);
        for (Edge e : right.outEdges) {
          rightNeighbors.add(e.to);
        }
        if (!rightNeighbors.contains(n)) {
          System.out.println("Got a rightt" + n.outEdges.size());
          Edge save;
          for (Edge r : n.outEdges) {
            if (r.to == right) {
              n.outEdges.remove(r);
              break;
            }
          }
        }
      }
      else if (n.x == this.nodesWide - 1) {
        System.out.println("right");
        up = this.maze2.get(i - 1);
        ArrayList<Node> upNeighbors = new ArrayList<Node>(0);
        for (Edge e : up.outEdges) {
          upNeighbors.add(e.to);
        }
        if (!upNeighbors.contains(n)) {
          System.out.println("Got a upr");
          for (Edge r : n.outEdges) {
            if (r.to == up) {
              n.outEdges.remove(r);
              break;
            }
          }
        }
      }
      else {
        System.out.println("middle");
        right = this.maze2.get(i + this.nodesTall);
        ArrayList<Node> rightNeighbors = new ArrayList<Node>(0);
        for (Edge e : right.outEdges) {
          rightNeighbors.add(e.to);
        }
        if (!rightNeighbors.contains(n)) {
          System.out.println("Got a rightt" + n.outEdges.size());
          Edge save;
          for (Edge r : n.outEdges) {
            if (r.to == right) {
              n.outEdges.remove(r);
              break;
            }
          }
        }
        up = this.maze2.get(i - 1);
        ArrayList<Node> upNeighbors = new ArrayList<Node>(0);
        for (Edge e : up.outEdges) {
          upNeighbors.add(e.to);
        }
        if (!upNeighbors.contains(n)) {
          System.out.println("Got a upr");
          for (Edge r : n.outEdges) {
            if (r.to == up) {
              n.outEdges.remove(r);
              break;
            }
          }
        }
      }
    }
  }

  void unmarkNodes(ArrayList<Node> arr) {
    for (Node n : arr) {
      n.marked = false;
      n.directPath = false;
      n.fullPath = false;
      n.parent = null;
    }
  }
}

class CompareLink implements IPred<Edge> {

  // do this edges connect the same nodes?
  public boolean apply(Edge t1, Edge t2) {
    return t1.connects(t2.from, t2.to);
  }

}

class SortWeight implements Comparator<Edge> {
  // Used for sorting in ascending order of
  // roll number
  public int compare(Edge a, Edge b) {
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
    return (int) (Math.random() * MazeWorld.RAND_RANGE);
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

  void initTest3x12() {
    ex1 = new MazeWorld(3, 12);
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
    this.initTest3x12();
    t.checkExpect(ex1.maze.size(), 3);
    t.checkExpect(ex1.maze.get(0).size(), 12);

  }

  // test Edge Creation
  void testInitEdges(Tester t) {
    this.initTest64();
    t.checkExpect(ex1.edges.size(), 8064);
    this.initTest3();
    t.checkExpect(ex1.edges.size(), 12);
  }

  // test the getValue method
  void testGetValue(Tester t) {
    this.initTest64();
    Utils<Integer> u = new Utils<Integer>();
    Posn p = new Posn(0, 1);
    Posn p2 = new Posn(1, 3);
    ArrayList<Integer> l1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
    ArrayList<Integer> l2 = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8));
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(Arrays.asList(l1, l2));
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
    Node n1 = new Node(new Posn(1, 1));
    Node n2 = new Node(new Posn(0, 0));
    t.checkExpect(n1.connect(n2).to, n2);
  }

  // test that posn's .equals() is in fact overridden
  void testPosn(Tester t) {
    Posn p1 = new Posn(1, 1);
    Posn p2 = new Posn(1, 1);
    t.checkExpect(p1.equals(p2), true);
  }

  // test init hash
  void testInitHash(Tester t) {
    this.initTest3();
    t.checkExpect(ex1.hash.get(ex1.maze2.get(0).xy), ex1.maze2.get(0));
    t.checkExpect(ex1.hash.get(ex1.maze2.get(6).xy), ex1.maze2.get(6));
  }

  // test is Equal in the Posn class
  void testIsEqual(Tester t) {
    Posn p1 = new Posn(0, 0);
    Posn p2 = new Posn(1, 0);
    Posn p3 = new Posn(1, 0);
    t.checkExpect(p1.equals(p2), false);
    t.checkExpect(p2.equals(p3), true);
  }

  // Test get Color
  void testGetColor(Tester t) {
    this.initTest3();
    Node n = new Node(new Posn(0, 0));
    t.checkExpect(n.getColor(ex1), Color.GREEN);
  }

  // test connects in the edge class
  void testConnects(Tester t) {
    Posn p = new Posn(0, 0);
    Node n1 = new Node(p);
    Node n2 = new Node(p);
    Node n3 = new Node(p);
    Edge e = new Edge(1, n1, n2);
    t.checkExpect(e.connects(n1, n2), true);
    t.checkExpect(e.connects(n2, n1), true);
    t.checkExpect(e.connects(n3, n1), false);
    t.checkExpect(e.connects(n1, n3), false);
  }

  // test the hasEdge method in the Node Class
  void testHasEdge(Tester t) {
    Posn p = new Posn(0, 0);
    Node n1 = new Node(p);
    Node n2 = new Node(p);
    Node n3 = new Node(p);
    n1.connect(n2);
    t.checkExpect(n1.hasEdge(n2), true);
    t.checkExpect(n1.hasEdge(n3), false);
    t.checkExpect(n1.hasEdge(null), false);
    t.checkExpect(n1.hasEdge(n1), false);

  }

  // test the CompareLink IPred
  void testCompareLink(Tester t) {
    IPred<Edge> p = new CompareLink();
    Posn posn = new Posn(0, 0);
    Node n1 = new Node(posn);
    Node n2 = new Node(posn);
    Node n3 = new Node(posn);
    Edge e = new Edge(1, n1, n2);
    Edge e1 = new Edge(10, n2, n1);
    Edge e2 = new Edge(10, n3, n3);
    t.checkExpect(p.apply(e, e1), true);
    t.checkExpect(p.apply(e, e2), false);

  }

  // test Kruskal's alg
  void testKruskal(Tester t) {
    this.initTest3();
    ex1.kruskalsAlg();
    t.checkExpect(ex1.walls.size(), ex1.maze2.size() - 1);
  }

  // Test the player movement
  void testPlayerMove(Tester t) {
    this.initTest3();
    Posn origin = new Posn(0, 0);
    Player p = ex1.player;
    p = p.movePlayer("up", ex1.maze2, ex1.walls);
    t.checkExpect(p.x, origin.x);
    t.checkExpect(p.y, origin.y); // can't go up from origin
    p = p.movePlayer("down", ex1.maze2, ex1.walls);
    t.checkExpect(p.x, origin.x);
    t.checkExpect(p.y, origin.y + 1);
    p = p.movePlayer("left", ex1.maze2, ex1.walls); // can't go left from origin
    t.checkExpect(p.x, origin.x);
    t.checkExpect(p.y, origin.y + 1);
    p = p.movePlayer("right", ex1.maze2, ex1.walls);
    t.checkExpect(p.x, origin.x + 1);
    t.checkExpect(p.y, origin.y + 1);
  }

  // Test the rendering
  void testRender(Tester t) {
    // these inputs represent the number of nodes in the maze
    ex1 = new MazeWorld(40, 60);
    ex1.initEmptyMaze();
    ex1.kruskalsAlg();
    ex1.fixOutEdges();
    ex1.breadthSearch(ex1.start);
    ex1.depthSearch(ex1.start);
    ex1.bigBang(ex1.nodesWide * MazeWorld.NODE_SIZE, ex1.nodesTall * MazeWorld.NODE_SIZE, .1);
  }
}