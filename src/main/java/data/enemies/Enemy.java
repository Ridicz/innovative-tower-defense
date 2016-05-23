package data.enemies;

import data.map.Tile;
import data.map.TileGrid;
import data.map.TileType;
import data.organisation.Player;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static data.helpers.Graphics.*;
import static data.map.Tile.TILE_SIZE;

public class Enemy {
  private final Logger logger = LoggerFactory.getLogger(Enemy.class);

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

  Animation explosionAnimation;

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

    setExplosionAnimation();
  }

  private void setExplosionAnimation() {
    try {
      this.explosionAnimation = new Animation(new SpriteSheet("src/main/java/res/explosion.png", 64, 64), 50);
      this.explosionAnimation.setAutoUpdate(true);
      explosionAnimation.stopAt(explosionAnimation.getFrameCount() - 1);
    } catch (RuntimeException e) {
      logger.error("Resources not found");
    } catch (SlickException e) {
      logger.error("Caught SlickException");
      e.printStackTrace();
    } finally {
      logger.info("Enemy resources loaded successfully");
    }
  }

  public void update() {
    if (!dying) {
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

      if (enemyEnteredNewTile()) {
        if (enemyEscapedMap()) {
          Player.decreaseLives();
          alive = false;
        } else {
          designateDirection();
        }
      }

      updateHealthBar();
    }
  }

  private boolean enemyEnteredNewTile() {
    return (xCoordinate % TILE_SIZE == 0 && yCoordinate % TILE_SIZE == 0);
  }

  private boolean enemyEscapedMap() {
    return (xCoordinate == endTile.getXCoordinate() && yCoordinate == endTile.getYCoordinate());
  }

  private void updateHealthBar() {
    healthBar.setXCoordinate(xCoordinate);
    healthBar.setYCoordinate(yCoordinate);

    designateHealthBarFill();
  }

  private void designateHealthBarFill() {
    healthBar.setHealthPercentage((int) ((health * 1.0) / maxHealth * 100));
  }

  public void draw() {
    if (dying) {
      animateExplosion();
    } else {
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
  }

  private void animateExplosion() {
    explosionAnimation.draw(xCoordinate, yCoordinate);
    if (explosionAnimation.isStopped()) {
      alive = false;
    }
  }

  public void hitEnemy(int damage) {
    health -= damage;

    if (health <= 0 && !dying) {
      Player.addMoney(reward);
      dying = true;
      logger.info("Enemy killed");
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

  public boolean isDying() {
    return dying;
  }

  public int getReward() {
    return reward;
  }

  private void designateDirection() { //TODO simplify for f. sake!
    if (tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE) != startTile) {
      if (tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE - 1).getTileType() == TileType.Dirt &&
        tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE - 1) != startTile) {
        direction = Direction.Up;
      } else if (tileGrid.getTile(xCoordinate / TILE_SIZE + 1, yCoordinate / TILE_SIZE).getTileType() == TileType.Dirt &&
        tileGrid.getTile(xCoordinate / TILE_SIZE + 1, yCoordinate / TILE_SIZE) != startTile) {
        direction = Direction.Right;
      } else if (tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE + 1).getTileType() == TileType.Dirt &&
        tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE + 1) != startTile) {
        direction = Direction.Down;
      } else {
        direction = Direction.Left;
      }

      startTile = tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE);
    }
  }
}
