package data;

import org.newdawn.slick.opengl.Texture;

abstract public class Projectile {
  private int xCoordinate;
  private int yCoordinate;
  private int damage;

  private Texture texture;

  private Enemy targetEnemy;

  public Projectile(int xCoordinate, int yCoordinate, int damage, Enemy targetEnemy, Texture texture) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.damage = damage;
    this.targetEnemy = targetEnemy;
    this.texture = texture;
  }

  abstract public void update();

  abstract public void draw();

  public Enemy getTargetEnemy() {
    return targetEnemy;
  }

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  public void setXCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public void setYCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

  public Texture getTexture() {
    return texture;
  }

  public int getDamage() {
    return damage;
  }
}
