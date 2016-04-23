package data;

import static helpers.Artist.*;

public class FastTower extends ConventionalTower {
  public FastTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    super(xCoordinate, yCoordinate, quickLoadTexture("fast_tower"), waveManager, 0.5f, 4, 150, 150, 5);
  }

  @Override
  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new FastTower(xCoordinate, yCoordinate, waveManager);
  }

  @Override
  public void upgrade() {

  }
}
