package data.towers;

import data.map.Tile;
import data.organisation.WaveManager;

import static data.helpers.Graphics.drawDoubleLaserBeam;
import static data.helpers.Graphics.quickLoadTexture;

public class HeavyLaserTower extends LaserTower {
  public HeavyLaserTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    super(xCoordinate, yCoordinate, waveManager, quickLoadTexture("tower2"), 3f, 35, 300, 450);
    this.startTexture = quickLoadTexture("laser/start_blue");
    this.middleTexture = quickLoadTexture("laser/middle_blue");
    this.endTexture = quickLoadTexture("laser/end_blue");
  }

  @Override
  public void draw() {
    super.draw();

    if (timeSinceLastShot < LASER_DURATION) {
      drawDoubleLaserBeam(xStart, yStart, Tile.TILE_SIZE, height - WW, rotation + 180, alpha, startTexture, middleTexture, endTexture);
    }
  }

  @Override
  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new HeavyLaserTower(xCoordinate, yCoordinate, waveManager);
  }

  @Override
  public void upgrade() {
    price += upgradePrice;
    range += 50;
  }
}
