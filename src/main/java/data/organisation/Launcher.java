package data.organisation;

import data.map.Tile;
import data.map.TileGrid;
import org.lwjgl.opengl.Display;

import static data.helpers.Graphics.*;
import static data.helpers.Clock.*;

public class Launcher {
  int fps;
  long lastFPS = getTime();

  public void updateFPS() {
    if (getTime() - lastFPS > 1000) {
      Display.setTitle("FPS: " + fps);
      fps = 0;
      lastFPS += 1000;
    }

    fps++;
  }

  private Launcher() {
    beginSession();

    int[][] map = {
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    TileGrid tileGrid = new TileGrid(map);

    Tile startTile = tileGrid.getTile(0, 7);
    Tile endTile = tileGrid.getTile(19, 8);

    Player player = new Player(startTile, endTile, tileGrid);

    while (!Display.isCloseRequested()) {
      updateFPS();
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
    new Launcher();
  }
}
