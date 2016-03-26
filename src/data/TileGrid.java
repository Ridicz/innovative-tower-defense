package data;

import static data.Tile.TILE_SIZE;

public class TileGrid {
  private static final int WIDTH_TILES = 20;
  private static final int HEIGHT_TILES = 10;

  public final static int MAP_WIDTH = TILE_SIZE * WIDTH_TILES;
  public final static int MAP_HEIGHT = TILE_SIZE * HEIGHT_TILES;

  private Tile[][] map;

  TileGrid(int[][] inputMap) {
    map = new Tile[WIDTH_TILES][HEIGHT_TILES];

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        switch (inputMap[j][i]) {
          case 0:
            map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TileType.Grass);
            break;

          case 1:
            map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TileType.Dirt);
            break;

          case 2:
            map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TileType.Water);
            break;
        }
      }
    }
  }

  Tile getTile(int xCoordinate, int yCoordinate) {
    return map[xCoordinate][yCoordinate];
  }

  public void draw() {
    for (Tile[] row : map) {
      for (Tile tile : row) {
        tile.draw();
      }
    }
  }
}
