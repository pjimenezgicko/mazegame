import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeExamples {
  MazeWorld ex1 = new MazeWorld(64, 64);
  
  void testMaze(Tester t) {
    this.ex1.initEmptyMaze();
    this.ex1.initHash();
    ex1.bigBang(ex1.height * MazeWorld.NODE_SIZE,
        ex1.width * MazeWorld.NODE_SIZE, .5);
 
  }
}