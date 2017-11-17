import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Node {
  Node up;
  Node right;
  Node down;
  Node left;
  
  Node(Node up, Node right, Node down, Node left) {
    this.up = up;
    this.right = right;
    this.down = down;
    this.left = left;
  }
}