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
  
  ArrayList<Edge> edges;
  
  HashMap<Posn, Node> hash;
  
  
  // 
  void initEmptyMaze() {
    this.maze = new ArrayList<ArrayList<Node>>(MAZE_SIZE);
    for (int i = 0; i < MAZE_SIZE; i++) {
      ArrayList<Node> temp = new ArrayList<Node>(MAZE_SIZE);
      for (int j = 0; j < MAZE_SIZE; j++) {
        temp.set(j, new Node(new Posn(i,j)));
      }
      this.maze.set(i, temp);
    }
    this.initEdges();
  }

  // EFFECT: Create edges for the maze 
  void initEdges() {
    Utils<Node> u = new Utils(); 
    // Edges for the top left corner
    this.edges.add(u.getValue(this.maze, new Posn(0,0)).connect(u.getValue(this.maze, new Posn(1,0))));
    this.edges.add(u.getValue(this.maze, new Posn(0,0)).connect(u.getValue(this.maze, new Posn(0,1))));

    // Edges for the top right corner
    int rightindex = this.maze.size() - 1;
    this.edges.add(u.getValue(this.maze, new Posn(rightindex,0)).connect(u.getValue(this.maze, new Posn(rightindex - 1,0))));
    this.edges.add(u.getValue(this.maze, new Posn(rightindex,0)).connect(u.getValue(this.maze, new Posn(rightindex,1))));

    // Edges for bottom left corner
    int bottomindex = this.maze.get(this.maze.size() - 1).size() - 1;
    this.edges.add(u.getValue(this.maze, new Posn(0,bottomindex)).connect(u.getValue(this.maze, new Posn(1,bottomindex))));
    this.edges.add(u.getValue(this.maze, new Posn(0,bottomindex)).connect(u.getValue(this.maze, new Posn(0,bottomindex-1))));

    // Edges for the bottom right corner
    this.edges.add(u.getValue(this.maze, new Posn(rightindex,bottomindex)).connect(u.getValue(this.maze, new Posn(rightindex-1,bottomindex))));
    this.edges.add(u.getValue(this.maze, new Posn(rightindex,bottomindex)).connect(u.getValue(this.maze, new Posn(rightindex,bottomindex-1))));

    // Edges for the left border
    for (int j = 1; j < this.maze.get(0).size() - 1; j++) {
      Posn p = new Posn(0,j);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(0,j-1)))); // top
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(0,j + 1)))); // bottom
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(1,j)))); // right
    }

    // Edges for the right border
    for (int j = 1; j < this.maze.get(this.maze.size() - 1).size() - 1; j++) {
      Posn p = new Posn(rightindex,j);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex,j-1)))); // top
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex,j+1)))); // bottom
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(rightindex - 1,j)))); // left
    }

    // Edges for the top border
    for (int i = 1; i < this.maze.get(this.maze.size() - 1).size() - 1; i++) {
      Posn p = new Posn(i, 0);
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i+1,0)))); // right
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i-1,0)))); // left 
      this.edges.add(u.getValue(this.maze, p).connect(u.getValue(this.maze, new Posn(i,1)))); // bottom
    }

    // Edges for the bottom border
    for (int i = 1; i < this.maze.get(this.maze.size() - 1).size() - 1; i++) {
      this.maze.get(i).get(bottomindex).right = this.maze.get(i + 1).get(bottomindex);
      this.maze.get(i).get(bottomindex).left = this.maze.get(i - 1).get(bottomindex);
      this.maze.get(i).get(bottomindex).top = this.maze.get(i).get(bottomindex - 1);
    }

    // fix the cell links for the rest of the array
    for (int i = 1; i < this.maze.size() - 1; i++) {
      for (int j = 1; j < this.maze.get(0).size() - 1; j++) {
        Node center = this.maze.get(i).get(j);
        center.top = this.maze.get(i).get(j - 1);
        center.bottom = this.maze.get(i).get(j + 1);
        center.right = this.maze.get(i + 1).get(j);
        center.left = this.maze.get(i - 1).get(j);
      }
    }

  }
  
  void initHash() {
    
  }
}

class Utils <T> {
  
  // get the value at a Posn from a given 2D array
  T getValue(ArrayList<ArrayList<T>> matrix, Posn p) {
    return matrix.get(p.x).get(p.y);
  }
  
  // return a random int
  int random() {
   return (int)Math.random()*100;
  }
}

class ExamplesMaze() {
  // -------------------------- Test Cell Linking ---------------------

  // test the link Cells method
  void testLinkCells(Tester t) {
    this.initTest();
    ex1.initCells();
    // test a cell in the center
    Node center = this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT)
        .get(MazeWorld.ISLAND_HEIGHT);
    t.checkExpect(center.left, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT - 1)
        .get(MazeWorld.ISLAND_HEIGHT));
    t.checkExpect(center.right, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT + 1)
        .get(MazeWorld.ISLAND_HEIGHT));
    t.checkExpect(center.top, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT)
        .get(MazeWorld.ISLAND_HEIGHT - 1));
    t.checkExpect(center.bottom, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT)
        .get(MazeWorld.ISLAND_HEIGHT + 1));

    // test a cell on the right border
    Node rightBorderCell = this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1)
        .get(MazeWorld.ISLAND_HEIGHT);
    t.checkExpect(rightBorderCell.right, rightBorderCell); // right border maze should be linked to
    // themselves on the right
    t.checkExpect(rightBorderCell.left, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 2)
        .get(MazeWorld.ISLAND_HEIGHT));
    t.checkExpect(rightBorderCell.top, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1)
        .get(MazeWorld.ISLAND_HEIGHT - 1));
    t.checkExpect(rightBorderCell.bottom, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1)
        .get(MazeWorld.ISLAND_HEIGHT + 1));

    // test a cell on the left border
    Node leftBorderCell = this.ex1.maze.get(0).get(MazeWorld.ISLAND_HEIGHT);
    t.checkExpect(leftBorderCell.right,
        this.ex1.maze.get(1).get(MazeWorld.ISLAND_HEIGHT));
    t.checkExpect(leftBorderCell.left, leftBorderCell);
    t.checkExpect(leftBorderCell.top,
        this.ex1.maze.get(0).get(MazeWorld.ISLAND_HEIGHT - 1));
    t.checkExpect(leftBorderCell.bottom,
        this.ex1.maze.get(0).get(MazeWorld.ISLAND_HEIGHT + 1));

    // test a cell on the bottom border
    Node bottomBorderCell = this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT)
        .get(MazeWorld.ISLAND_SIZE - 1);
    t.checkExpect(bottomBorderCell.right, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT + 1)
        .get(MazeWorld.ISLAND_SIZE - 1));
    t.checkExpect(bottomBorderCell.left, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT - 1)
        .get(MazeWorld.ISLAND_SIZE - 1));
    t.checkExpect(bottomBorderCell.top, this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT)
        .get(MazeWorld.ISLAND_SIZE - 2));
    t.checkExpect(bottomBorderCell.bottom, bottomBorderCell);

    // test a cell on the top border
    Node topBorderCell = this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT).get(0);
    t.checkExpect(topBorderCell.right,
        this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT + 1).get(0));
    t.checkExpect(topBorderCell.left,
        this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT - 1).get(0));
    t.checkExpect(topBorderCell.top, topBorderCell);
    t.checkExpect(topBorderCell.bottom,
        this.ex1.maze.get(MazeWorld.ISLAND_HEIGHT).get(1));

    // test top right corner
    Node topRight = this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1).get(0);
    t.checkExpect(topRight.right, topRight);
    t.checkExpect(topRight.left, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 2).get(0));
    t.checkExpect(topRight.top, topRight);
    t.checkExpect(topRight.bottom, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1).get(1));

    // test top left corner
    Node topLeft = this.ex1.maze.get(0).get(0);
    t.checkExpect(topLeft.right, this.ex1.maze.get(1).get(0));
    t.checkExpect(topLeft.left, topLeft);
    t.checkExpect(topLeft.top, topLeft);
    t.checkExpect(topLeft.bottom, this.ex1.maze.get(0).get(1));

    // test the bottom right corner
    Node bottomRight = this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1)
        .get(MazeWorld.ISLAND_SIZE - 1);
    t.checkExpect(bottomRight.right, bottomRight);
    t.checkExpect(bottomRight.left, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 2)
        .get(MazeWorld.ISLAND_SIZE - 1));
    t.checkExpect(bottomRight.top, this.ex1.maze.get(MazeWorld.ISLAND_SIZE - 1)
        .get(MazeWorld.ISLAND_SIZE - 2));
    t.checkExpect(bottomRight.bottom, bottomRight);

    // test the bottom left corner
    Node bottomLeft = this.ex1.maze.get(0).get(MazeWorld.ISLAND_SIZE - 1);
    t.checkExpect(bottomLeft.right,
        this.ex1.maze.get(1).get(MazeWorld.ISLAND_SIZE - 1));
    t.checkExpect(bottomLeft.left, bottomLeft);
    t.checkExpect(bottomLeft.top, this.ex1.maze.get(0).get(MazeWorld.ISLAND_SIZE - 2));
    t.checkExpect(bottomLeft.bottom, bottomLeft);

  }

}