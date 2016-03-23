package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class LaserTower extends Tower {
  private static final float LASER_DURATION = 1;

  private static final int WW = Tile.TILE_SIZE / 2;

  private double alpha;

  private int height;

  private Texture startTexture;
  private Texture middleTexture;
  private Texture endTexture;

  public LaserTower(int xCoordinate, int yCoordinate, WaveManager waveManager, Texture textureCannon, Texture startTexture,
                    Texture middleTexture, Texture endTexture, float reloadTime, int damage, int range, int cost) {
    super(xCoordinate, yCoordinate, textureCannon, waveManager, reloadTime, damage, range, cost);

    this.startTexture = startTexture;
    this.middleTexture = middleTexture;
    this.endTexture = endTexture;
  }

  public LaserTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    this(xCoordinate, yCoordinate, waveManager, quickLoadTexture("tower1"), quickLoadTexture("laser/start_red"),
      quickLoadTexture("laser/middle_red"), quickLoadTexture("laser/end_red"), 1f, 10, 280, 200);
  }

  @Override
  public void update() {
    super.update();

    if (shotTaken()) {
      super.getTargetEnemy().hitEnemy(super.getDamage());
    }

    alpha = 1 - (super.getTimeSinceLastShot() / LASER_DURATION) * (super.getTimeSinceLastShot() / LASER_DURATION) * 3;
  }

  public void draw() {
    super.draw();

    if (super.getTimeSinceLastShot() < LASER_DURATION) {
      double radians = Math.toRadians(super.getRotation());
      double sinAlfa = Math.sin(radians);
      double cosAlfa = Math.cos(radians);

      if (super.getTargetEnemy() != null) {
        height = (int) Math.sqrt((super.getTargetEnemy().getXCoordinate() - super.getXCoordinate()) * (super.getTargetEnemy().getXCoordinate() - super.getXCoordinate()) +
          (super.getTargetEnemy().getYCoordinate() - super.getYCoordinate()) * (super.getTargetEnemy().getYCoordinate() - super.getYCoordinate()));
      }

//      int xStart = (int) (8 * sinAlfa + super.getXCoordinate() + 32 + 32 * cosAlfa);
//      int yStart = (int) (- 8 * cosAlfa + super.getYCoordinate() + 32 + 32 * sinAlfa);
//
//      drawLaserBeam(xStart, yStart, 64, height - 32, super.getRotation() + 180, alpha, startTexture, middleTexture, endTexture);

      int xStart = (int) (8 * sinAlfa + super.getXCoordinate() + WW + WW * cosAlfa);
      int yStart = (int) (- 8 * cosAlfa + super.getYCoordinate() + WW + WW * sinAlfa);

      drawLaserBeam(xStart, yStart, Tile.TILE_SIZE, height - WW, super.getRotation() + 180, alpha, startTexture, middleTexture, endTexture);


    }
  }

  @Override
  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new LaserTower(xCoordinate, yCoordinate, waveManager);
  }
}
