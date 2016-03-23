package data;

import helpers.Clock;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static data.Tile.TILE_SIZE;

public class Enemy {
  private static final int ENEMY_SIZE = TILE_SIZE;

  private boolean alive;
  private boolean dying;

  private float speed;

  private int xCoordinate;
  private int yCoordinate;
  private int width;
  private int height;
  private int health;
  private int maxHealth;
  private int reward;

  private Tile startTile;
  private Tile endTile;

  private Texture texture;

  private HealthBar healthBar;

  private TileGrid tileGrid;

  private Direction direction = Direction.Right;

  public Enemy(Tile startTile, Tile endTile, TileGrid tileGrid, float speed, Texture texture, int maxHealth, int reward) {
    this.startTile = startTile;
    this.endTile = endTile;
    this.xCoordinate = startTile.getXCoordinate();
    this.yCoordinate = startTile.getYCoordinate();
    this.width = ENEMY_SIZE;
    this.height = ENEMY_SIZE;
    this.speed = speed;
    this.texture = texture;
    this.tileGrid = tileGrid;
    this.alive = true;
    this.dying = false;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.healthBar = new HealthBar(xCoordinate, yCoordinate);
    this.reward = reward;
  }

  public void update() {
    switch (direction) {
      case Right:
        xCoordinate += speed;
        break;
      case Up:
        yCoordinate -= speed;
        break;
      case Down:
        yCoordinate += speed;
        break;
      case Left:
        xCoordinate -= speed;
        break;
    }

    if (xCoordinate % TILE_SIZE == 0 && yCoordinate % TILE_SIZE == 0) {
      if (xCoordinate == endTile.getXCoordinate() && yCoordinate == endTile.getYCoordinate()) {
        Player.decreaseLives();
        alive = false;
      } else {
        if (tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE) != startTile) {
          if (tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE - 1).getTileType() == TileType.Dirt && tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE - 1) != startTile) {
            direction = Direction.Up;
          } else if (tileGrid.getTile(xCoordinate / TILE_SIZE + 1, yCoordinate / TILE_SIZE).getTileType() == TileType.Dirt && tileGrid.getTile(xCoordinate / TILE_SIZE + 1, yCoordinate / TILE_SIZE) != startTile) {
            direction = Direction.Right;
          } else if (tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE + 1).getTileType() == TileType.Dirt && tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE + 1) != startTile) {
            direction = Direction.Down;
          } else {
            direction = Direction.Left;
          }

          startTile = tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE);
        }
      }
    }

    healthBar.setXCoordinate(xCoordinate);
    healthBar.setYCoordinate(yCoordinate);
    healthBar.setHealthPercentage((int) ((health * 1.0) / maxHealth * 100));
  }

  public void draw() {
    switch (direction) {
      case Down:
        drawQuadTexRotated2(xCoordinate, yCoordinate, width, height, 180, texture);
        break;

      case Left:
        drawQuadTexRotated2(xCoordinate, yCoordinate, width, height, 270, texture);
        break;

      case Right:
        drawQuadTexRotated2(xCoordinate, yCoordinate, width, height, 90, texture);
        break;

      case Up:
        drawQuadTexRotated2(xCoordinate, yCoordinate, width, height, 0, texture);
        break;
    }

    healthBar.draw();
  }

  public void hitEnemy(int damage) {
    health -= damage;

    if (health <= 0) {
      Player.addMoney(reward);
      //drawText(xCoordinate, yCoordinate, "$" + reward);
      alive = false;
    }
  }

  public float getSpeed() {
    return speed;
  }

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  public Texture getTexture() {
    return texture;
  }

  public Tile getStartTile() {
    return startTile;
  }

  public Tile getEndTile() {
    return endTile;
  }

  public TileGrid getTileGrid() {
    return tileGrid;
  }

  public int getHealth() {
    return health;
  }

  public boolean isAlive() {
    return alive;
  }

  public int getReward() {
    return reward;
  }
}
