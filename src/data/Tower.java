package data;

import org.newdawn.slick.opengl.Texture;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;
import static helpers.Artist.*;
import static data.Tile.TILE_SIZE;

public abstract class Tower {
  private static final int TOWER_SIZE = TILE_SIZE;

  private final int DAMAGE;
  private final int RANGE;
  private final int PRICE;

  private final float RELOAD_TIME;

  private int xCoordinate;
  private int yCoordinate;

  private float rotation;
  private float rotationSpeed;
  private float timeSinceLastShot;

  private Texture textureCannon;

  private Tile tilePlaced;

  private Enemy targetEnemy;

  private WaveManager waveManager;

  public Tower(int xCoordinate, int yCoordinate, Texture textureCannon, WaveManager waveManager, float reloadTime, int damage, int range, int cost) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.timeSinceLastShot = 100;
    this.textureCannon = textureCannon;
    this.waveManager = waveManager;
    this.tilePlaced = null;
    this.RELOAD_TIME = reloadTime;
    this.DAMAGE = damage;
    this.RANGE = range;
    this.PRICE = cost;
    this.rotation = 0;
  }

  public void update() {
    targetEnemy = getTargetInSight();

    if (targetEnemy != null) {
      rotation = designateAngle();
    }
  }

  public void draw() {
    drawQuadTexRotated2(xCoordinate, yCoordinate, TOWER_SIZE, TOWER_SIZE, rotation, textureCannon);
  }

  public boolean shotTaken() {
    if (targetEnemy != null) {
      if (timeSinceLastShot >= RELOAD_TIME) {
        timeSinceLastShot = 0;
        return true;
      }
    }

    timeSinceLastShot += getDelta();

    return false;
  }

  public abstract Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager);

  public Tile getTilePlaced() {
    return tilePlaced;
  }

  public void setTilePlaced(Tile tilePlaced) {
    this.tilePlaced = tilePlaced;
  }

  public int getPrice() {
    return PRICE;
  }

  public int getRange() {
    return RANGE;
  }

  public void setXCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public void setYCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  public float getRotation() {
    return rotation;
  }

  public Enemy getTargetEnemy() {
    return targetEnemy;
  }

  public int getDamage() {
    return DAMAGE;
  }

  public float getTimeSinceLastShot() {
    return timeSinceLastShot;
  }

  private float designateAngle() {
    double angle = Math.atan2(targetEnemy.getYCoordinate() - yCoordinate, targetEnemy.getXCoordinate() - xCoordinate);

    return (float) Math.toDegrees(angle) + 90;
  }

  private Enemy getTargetInSight() {
    CopyOnWriteArrayList<Enemy> enemyList = waveManager.getEnemyList();

    for (Enemy enemy : enemyList) {
      if (!enemy.isDying() && Math.sqrt((enemy.getXCoordinate() - xCoordinate) * (enemy.getXCoordinate() - xCoordinate) +
        (enemy.getYCoordinate() - yCoordinate) * (enemy.getYCoordinate() - yCoordinate)) <= RANGE) {
        return enemy;
      }
    }

    return null;
  }
}
