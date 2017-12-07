import java.awt.Color;
import java.util.ArrayList;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// class to represent the player in the game 
class Player {

  // current location of the player
  Posn location;
  int x;
  int y;
  Node node;

  Player(Node node) {
    this.node = node;
    this.location = node.xy;
    this.x = node.x;
    this.y = node.y;
  }

  // Draws this player onto the background
  WorldImage drawAt(WorldImage background) {
    return new OverlayImage(new RectangleImage(MazeWorld.NODE_SIZE - 1, MazeWorld.NODE_SIZE - 1,
        OutlineMode.SOLID, Color.ORANGE), background);
  }

  // move this player in the given direction
  // Effect: update the current node as visited
  public Player movePlayer(String direction, ArrayList<Node> maze, ArrayList<Edge> walls) {

    int speed = 1;
    Posn target;
    Node goTo = this.node; // if goTo node is still this.node at the end we won't move

    if (direction.equals("up")) {
      target = new Posn(this.x, this.y - speed);
    }
    else if (direction.equals("down")) {
      target = new Posn(this.x, this.location.y + speed);
    }
    else if (direction.equals("right")) {
      target = new Posn(this.location.x + speed, this.y);
    }
    else if (direction.equals("left")) {
      target = new Posn(this.location.x - speed, this.y);
    }
    else {
      throw new IllegalArgumentException("Not a valid direction to move");
    }

    // check if there is a node with the desired Posn
    for (Node n : maze) {
      // look for a node with our target
      if (n.xy.equals(target)) {
        goTo = n;
      }
    }

    // if there is a wall between this node and the desired position we cannot
    // go there, so return this
    for (Edge e : walls) {
      if (e.connects(this.node, goTo)) {
        return this;
      }
    }
    // there are no walls blocking our way, move us there
    this.node.visited = true;
    return new Player(goTo);

  }
}
