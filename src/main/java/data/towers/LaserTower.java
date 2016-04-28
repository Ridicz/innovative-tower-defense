package data.towers;

import data.map.Tile;
import data.organisation.WaveManager;
import org.newdawn.slick.opengl.Texture;

import static data.helpers.Graphics.*;

public abstract class LaserTower extends Tower {
  protected static final float LASER_DURATION = 1;

  protected static final int WW = Tile.TILE_SIZE / 2;

  protected double alpha;

  protected int xStart;
  protected int yStart;
  protected int height;

  protected Texture startTexture;
  protected Texture middleTexture;
  protected Texture endTexture;

  public LaserTower(int xCoordinate, int yCoordinate, WaveManager waveManager, Texture textureCannon,
                    float reloadTime, int damage, int range, int cost) {
    super(xCoordinate, yCoordinate, textureCannon, waveManager, reloadTime, damage, range, cost);
  }

  @Override
  public void update() {
    super.update();

    if (shotTaken()) {
      targetEnemy.hitEnemy(damage);
    }

    if (timeSinceLastShot < LASER_DURATION) {
      designateBeamData();
    }

    alpha = 1 - (timeSinceLastShot / LASER_DURATION) * (timeSinceLastShot / LASER_DURATION) * 3;
  }

  protected void designateBeamData() {
    double radians = Math.toRadians(rotation);
    double sinAlfa = Math.sin(radians);
    double cosAlfa = Math.cos(radians);

    xStart = (int) (8 * sinAlfa + xCoordinate + WW + WW * cosAlfa);
    yStart = (int) (- 8 * cosAlfa + yCoordinate + WW + WW * sinAlfa);

    if (targetEnemy != null) {
      height = (int) Math.sqrt((targetEnemy.getXCoordinate() - xCoordinate) * (targetEnemy.getXCoordinate() - xCoordinate) +
        (targetEnemy.getYCoordinate() - yCoordinate) * (targetEnemy.getYCoordinate() - yCoordinate));
    }
  }
}
