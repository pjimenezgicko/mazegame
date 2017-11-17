import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Node {
  Node up;
  Node right;
  Node down;
  Node left;
  
  int x;
  int y;
  
  Node(Node up, Node right, Node down, Node left, int x, int y) {
    this.up = up;
    this.right = right;
    this.down = down;
    this.left = left;
    this.x = x;
    this.y = y;
    
  }
}