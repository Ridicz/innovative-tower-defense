package data.towers;

import data.enemies.Enemy;

import static data.helpers.Graphics.*;

public class LaserProjectile extends Projectile {
  private double rotation;

  private int height;

  public LaserProjectile(int xCoordinate, int yCoordinate, int damage, Enemy targetEnemy) {
    super(xCoordinate, yCoordinate, damage, targetEnemy, quickLoadTexture("laser"));
  }

  @Override
  public void update() {
    rotation = Math.atan2(super.getTargetEnemy().getYCoordinate() - super.getYCoordinate(), super.getTargetEnemy().getXCoordinate() - super.getXCoordinate());

    height = (int) Math.sqrt((super.getTargetEnemy().getXCoordinate() - super.getXCoordinate()) * (super.getTargetEnemy().getXCoordinate() - super.getXCoordinate()) +
      (super.getTargetEnemy().getYCoordinate() - super.getYCoordinate()) * (super.getTargetEnemy().getYCoordinate() - super.getYCoordinate()));
  }

  @Override
  public void draw() {
    drawQuadTexRotated(super.getXCoordinate(), super.getYCoordinate(), 16, height, (float) Math.toDegrees(rotation) + 90, super.getTexture());
  }
}
