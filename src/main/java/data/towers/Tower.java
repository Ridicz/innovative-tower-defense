package data.towers;

import data.helpers.Graphics;
import data.map.Tile;
import data.organisation.WaveManager;
import data.enemies.Enemy;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import java.util.concurrent.CopyOnWriteArrayList;

import static data.helpers.Clock.*;
import static data.helpers.Graphics.*;
import static data.map.Tile.TILE_SIZE;

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

  private Texture rangeTexture;

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
    this.rangeTexture = quickLoadTexture("range");
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

  public void drawRange(Tile towersLocationTile, int range) {
    drawRange(towersLocationTile.getXCoordinate(), towersLocationTile.getYCoordinate(), range);
  }

  public void drawRange(int xCoordinate, int yCoordinate, int range) {
    float scaledRange = (float) ((range * 2) * (TILE_SIZE / 64.0));
    float scaledCoordinates = (scaledRange - TILE_SIZE) / 2;

    drawQuadTex(xCoordinate - scaledCoordinates, yCoordinate - scaledCoordinates, scaledRange, scaledRange, rangeTexture);
  }
}
