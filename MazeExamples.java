import java.util.ArrayList;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class MazeExamples {
  MazeWorld ex1 = new MazeWorld();
  
  
  
  
  void testMaze(Tester t) {
    this.ex1.initEmptyMaze();
    this.ex1.initHash();
    ex1.bigBang(MazeWorld.MAZE_SIZE * MazeWorld.NODE_SIZE,
        MazeWorld.MAZE_SIZE * MazeWorld.MAZE_SIZE, .5);
 
  }
}