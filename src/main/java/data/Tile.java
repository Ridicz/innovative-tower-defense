package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class Tile {
  static final int TILE_SIZE = 64;

  private int xCoordinate;
  private int yCoordinate;

  private TileType tileType;

  private Texture texture;

  private Tower tower;

  public Tile(int xCoordinate, int yCoordinate, TileType tileType) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.tileType = tileType;
    this.texture = quickLoadTexture(tileType.textureName);
    this.tower = null;
  }

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  public void draw() {
    drawQuadTex(xCoordinate, yCoordinate, texture);
  }

  public void setTower(Tower tower) {
    this.tower = tower;
  }

  public Tower getTower() {
    return tower;
  }

  TileType getTileType() {
    return tileType;
  }
}
