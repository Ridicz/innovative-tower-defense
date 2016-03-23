package data;

import org.lwjgl.opengl.Display;

import static helpers.Artist.*;
import static helpers.Clock.*;

public class Boot {
  public Boot() {
    beginSession();

    int[][] map = {
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1},
      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    TileGrid tileGrid = new TileGrid(map);

    Tile startTile = tileGrid.getTile(0, 7);
    Tile endTile = tileGrid.getTile(14, 6);

    Player player = new Player(startTile, endTile, tileGrid);

    while (!Display.isCloseRequested()) {
      update();

      player.update();
      tileGrid.draw();
      player.draw();

      Display.update();
      Display.sync(60);
    }

    Display.destroy();
  }

  public static void main(String[] args) {
    new Boot();
  }
}
