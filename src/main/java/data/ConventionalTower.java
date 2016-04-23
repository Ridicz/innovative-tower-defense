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

    if (shotTaken()) {
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
    double radians = Math.toRadians(rotation);

    int xStart = (int) (32 * Math.sin(radians) + xCoordinate + 24);
    int yStart =  (int) (- 32 * Math.cos(radians) + yCoordinate + 24);

    projectiles.add(new BulletProjectile(xStart, yStart, damage, MUZZLE_VELOCITY, targetEnemy));

    //Lesson1.execute();
  }
}
