package data.towers;

import data.map.Tile;
import data.organisation.WaveManager;

import static data.helpers.Graphics.drawSingleLaserBeam;
import static data.helpers.Graphics.quickLoadTexture;

public class FastLaserTower extends LaserTower {
  public FastLaserTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    super(xCoordinate, yCoordinate, waveManager, quickLoadTexture("tower1"), 1f, 10, 260, 200);
    this.startTexture = quickLoadTexture("laser/start_red");
    this.middleTexture = quickLoadTexture("laser/middle_red");
    this.endTexture = quickLoadTexture("laser/end_red");
  }

  @Override
  public void draw() {
    super.draw();

    if (timeSinceLastShot < LASER_DURATION) {
      drawSingleLaserBeam(xStart, yStart, Tile.TILE_SIZE, height - WW, rotation + 180, alpha, startTexture, middleTexture, endTexture);
    }
  }

  @Override
  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new FastLaserTower(xCoordinate, yCoordinate, waveManager);
  }

  @Override
  public void upgrade() {
    price += upgradePrice;
    range += 50;
  }
}
