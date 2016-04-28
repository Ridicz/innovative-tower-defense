package data.towers;

import data.enemies.Enemy;

import static data.helpers.Graphics.*;
import static data.map.Tile.TILE_SIZE;

public class BulletProjectile extends Projectile {
  private static final int BULLET_SIZE = TILE_SIZE / 4;
  private static final int HALF_OF_SIZE = BULLET_SIZE / 2;
  private static final int BULLET_CONST = TILE_SIZE / 2 - HALF_OF_SIZE;
  private static final int DISTANCE_CONST = TILE_SIZE / 2 + HALF_OF_SIZE;

  private float speed;

  private boolean alive;

  public BulletProjectile(int xCoordinate, int yCoordinate, int damage, float speed, Enemy targetEnemy) {
    super(xCoordinate, yCoordinate, damage, targetEnemy, quickLoadTexture("projectile"));
    this.speed = speed;
    this.alive = true;
  }

  @Override
  public void update() {
    if (alive) {
      if (!super.getTargetEnemy().isAlive()) {
        alive = false;
      }

      if (checkForCollisionWithTarget()) {
        super.getTargetEnemy().hitEnemy(super.getDamage());
        alive = false;
      } else {
        designateMovement();
      }
    }
  }

  @Override
  public void draw() {
    drawQuadTex(super.getXCoordinate(), super.getYCoordinate(), BULLET_SIZE, BULLET_SIZE, super.getTexture());
  }

  private void designateMovement() {
    double distance = Math.sqrt((super.getTargetEnemy().getXCoordinate() + 32 - super.getXCoordinate() - 8) * (super.getTargetEnemy().getXCoordinate() + 32 - super.getXCoordinate() - 8)
      + (super.getTargetEnemy().getYCoordinate() + 32 - super.getYCoordinate() - 8) * (super.getTargetEnemy().getYCoordinate() + 32 - super.getYCoordinate() - 8));

    double sinAlfa = Math.abs(super.getTargetEnemy().getXCoordinate() - super.getXCoordinate() + BULLET_CONST) / distance;
    double cosAlfa = Math.abs(super.getTargetEnemy().getYCoordinate() - super.getYCoordinate() + BULLET_CONST) / distance;

    if (super.getXCoordinate() < super.getTargetEnemy().getXCoordinate() + BULLET_CONST) {
      super.setXCoordinate((int) (super.getXCoordinate() + speed * sinAlfa));
    } else {
      super.setXCoordinate((int) (super.getXCoordinate() - speed * sinAlfa));
    }

    if (super.getYCoordinate() < super.getTargetEnemy().getYCoordinate() + BULLET_CONST) {
      super.setYCoordinate((int) (super.getYCoordinate() + speed * cosAlfa));
    } else {
      super.setYCoordinate((int) (super.getYCoordinate() - speed * cosAlfa));
    }
  }

  public boolean isAlive() {
    return alive;
  }

  private boolean checkForCollisionWithTarget() {
    double distance = Math.sqrt((super.getTargetEnemy().getXCoordinate() - super.getXCoordinate() + BULLET_CONST) * (super.getTargetEnemy().getXCoordinate() - super.getXCoordinate() + BULLET_CONST)
      + (super.getTargetEnemy().getYCoordinate() - super.getYCoordinate() + BULLET_CONST) * (super.getTargetEnemy().getYCoordinate() - super.getYCoordinate() + BULLET_CONST));

    //TODO change shape of enemy checking circle->square

    return distance <= DISTANCE_CONST;
  }
}
