package data;

import static helpers.Artist.quickLoadTexture;

public class HeavyLaserTower extends LaserTower {
  public HeavyLaserTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    super(xCoordinate, yCoordinate, waveManager, quickLoadTexture("tower2"), quickLoadTexture("laser/start_blue"),
      quickLoadTexture("laser/middle_blue"), quickLoadTexture("laser/end_blue"), 3f, 35, 300, 450);
  }

  @Override
  public Tower getNewTower(int xCoordinate, int yCoordinate, WaveManager waveManager) {
    return new HeavyLaserTower(xCoordinate, yCoordinate, waveManager);
  }
}
