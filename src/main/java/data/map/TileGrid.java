package data.map;

public class TileGrid {
  private static final int WIDTH_TILES = 20;
  private static final int HEIGHT_TILES = 10;

  public final static int MAP_WIDTH = Tile.TILE_SIZE * WIDTH_TILES;
  public final static int MAP_HEIGHT = Tile.TILE_SIZE * HEIGHT_TILES;

  private Tile[][] map;

  public TileGrid(int[][] inputMap) {
    map = new Tile[WIDTH_TILES][HEIGHT_TILES];

    initializeMap(inputMap);
  }

  private void initializeMap(int[][] inputMap) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        switch (inputMap[j][i]) {
          case 0:
            map[i][j] = new Tile(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE, TileType.Grass);
            break;

          case 1:
            map[i][j] = new Tile(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE, TileType.Dirt);
            break;

          case 2:
            map[i][j] = new Tile(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE, TileType.Water);
            break;
        }
      }
    }
  }

  public Tile getTile(int xCoordinate, int yCoordinate) {
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
