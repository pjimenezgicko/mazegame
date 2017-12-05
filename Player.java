import java.awt.Color;

import javalib.worldimages.CircleImage;
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

  Player(Posn loc) {
    this.location = loc;
    this.x = loc.x;
    this.y = loc.y;
  }

  // Draws this player onto the background
  WorldImage drawAt(WorldImage background) {
    return new OverlayImage(new RectangleImage(MazeWorld.NODE_SIZE-1, MazeWorld.NODE_SIZE-1,
        OutlineMode.SOLID, Color.ORANGE), background);
  }

  // move this player in the given direction
  public Player movePlayer(String direction) {

    int speed = 1;
    Posn target;

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

    return new Player(target);
  }
}
