package data;

import org.newdawn.slick.opengl.Texture;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;
import static helpers.Artist.*;
import static data.Tile.TILE_SIZE;

public abstract class Tower {
  private static final int TOWER_SIZE = TILE_SIZE;

  private int xCoordinate;
  private int yCoordinate;
  private int damage;
  private int range;
  private int price;

  private float reloadTime;
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
    this.reloadTime = reloadTime;
    this.damage = damage;
    this.range = range;
    this.price = cost;
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

  boolean shotTaken() {
    if (targetEnemy != null) {
      if (timeSinceLastShot >= reloadTime) {
        timeSinceLastShot = 0;
        return true;
      }
    }

    timeSinceLastShot += getDelta();

    return false;
  }

  public abstract Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager);

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  Tile getTilePlaced() {
    return tilePlaced;
  }

  void setTilePlaced(Tile tilePlaced) {
    this.tilePlaced = tilePlaced;
  }

  int getPrice() {
    return price;
  }

  int getRange() {
    return range;
  }

  void setXCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  void setYCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

  float getRotation() {
    return rotation;
  }

  Enemy getTargetEnemy() {
    return targetEnemy;
  }

  int getDamage() {
    return damage;
  }

  float getTimeSinceLastShot() {
    return timeSinceLastShot;
  }

  float getReloadTime() {
    return reloadTime;
  }

  private float designateAngle() {
    double angle = Math.atan2(targetEnemy.getYCoordinate() - yCoordinate, targetEnemy.getXCoordinate() - xCoordinate);

    return (float) Math.toDegrees(angle) + 90;
  }

  private Enemy getTargetInSight() {
    CopyOnWriteArrayList<Enemy> enemyList = waveManager.getEnemyList();

    for (Enemy enemy : enemyList) {
      if (!enemy.isDying() && Math.sqrt((enemy.getXCoordinate() - xCoordinate) * (enemy.getXCoordinate() - xCoordinate) +
        (enemy.getYCoordinate() - yCoordinate) * (enemy.getYCoordinate() - yCoordinate)) <= range) {
        return enemy;
      }
    }

    return null;
  }
}
