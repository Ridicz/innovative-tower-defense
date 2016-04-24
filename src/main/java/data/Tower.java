package data;

import org.newdawn.slick.opengl.Texture;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;
import static helpers.Artist.*;
import static data.Tile.TILE_SIZE;

public abstract class Tower {
  private static final int TOWER_SIZE = TILE_SIZE;

  protected int xCoordinate;
  protected int yCoordinate;
  protected int damage;
  protected int range;
  protected int price;
  protected int upgradePrice;

  protected float reloadTime;
  protected float rotation;
  protected float rotationSpeed;
  protected float timeSinceLastShot;

  private Texture textureCannon;

  private Tile tilePlaced;

  protected Enemy targetEnemy;

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
    this.upgradePrice = (int) (price * 0.2);
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
      if (timeSinceLastShot >= reloadTime) {
        timeSinceLastShot = 0;
        return true;
      }
    }

    timeSinceLastShot += getDelta();

    return false;
  }

  public abstract void upgrade();

  public void upgrade(int rangeIncrease, int damageIncrease, int priceIncrease) {
    this.range += rangeIncrease;
    this.damage += damageIncrease;
    this.price += priceIncrease;
  }

  public abstract Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager);

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  public Tile getTilePlaced() {
    return tilePlaced;
  }

  public void setTilePlaced(Tile tilePlaced) {
    this.tilePlaced = tilePlaced;
  }

  public int getPrice() {
    return price;
  }

  public int getRange() {
    return range;
  }

  public void setXCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public void setYCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

  public int getDamage() {
    return damage;
  }

  public float getReloadTime() {
    return reloadTime;
  }

  public int getUpgradePrice() {
    return upgradePrice;
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
