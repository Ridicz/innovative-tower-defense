package data;

import static helpers.Artist.quickLoadTexture;

public class BigAssTower extends ConventionalTower {
  BigAssTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    super(xCoordinate, yCoordinate, quickLoadTexture("tower3"), waveManager, 2f, 100, 200, 800, 5);
  }

  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new BigAssTower(xCoordinate, yCoordinate, waveManager);
  }

  @Override
  public void upgrade() {

  }
}
