package data.towers;

import data.organisation.WaveManager;

import static data.helpers.Graphics.quickLoadTexture;

public class HeavyTower extends ConventionalTower {
  public HeavyTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    super(xCoordinate, yCoordinate, quickLoadTexture("heavy_tower"), waveManager, 1.5f, 35, 250, 300, 5);
  }

  @Override
  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new HeavyTower(xCoordinate, yCoordinate, waveManager);
  }

  @Override
  public void upgrade() {

  }
}
