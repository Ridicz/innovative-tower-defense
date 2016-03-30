package data;

import helpers.Lesson1;
import org.newdawn.slick.opengl.Texture;

import java.util.concurrent.CopyOnWriteArrayList;

abstract public class ConventionalTower extends Tower {
  private final float MUZZLE_VELOCITY;

  private CopyOnWriteArrayList<BulletProjectile> projectiles;

  public ConventionalTower(int xCoordinate, int yCoordinate, Texture texture, WaveManager waveManager, float reloadTime, int damage, int range, int cost, float muzzleVelocity) {
    super(xCoordinate, yCoordinate, texture, waveManager, reloadTime, damage, range, cost);
    this.MUZZLE_VELOCITY = muzzleVelocity;
    this.projectiles = new CopyOnWriteArrayList<>();
  }

  public void update() {
    super.update();

    if (super.shotTaken()) {
      shoot();
    }

    for (BulletProjectile projectile : projectiles) {
      if (projectile.isAlive()) {
        projectile.update();
        projectile.draw();
      } else {
        projectiles.remove(projectile);
      }
    }
  }

  public void draw() {
    super.draw();
    projectiles.forEach(Projectile::draw);
  }

  private void shoot() {
    double radians = Math.toRadians(super.getRotation());

    int xStart = (int) (32 * Math.sin(radians) + super.getXCoordinate() + 24);
    int yStart =  (int) (- 32 * Math.cos(radians) + super.getYCoordinate() + 24);

    projectiles.add(new BulletProjectile(xStart, yStart, super.getDamage(), MUZZLE_VELOCITY, super.getTargetEnemy()));

    //Lesson1.execute();
  }
}
