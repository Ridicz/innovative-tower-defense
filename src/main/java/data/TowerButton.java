package data;

import org.newdawn.slick.opengl.Texture;

public class TowerButton extends Button {
  private Tower tower;

  private WaveManager waveManager;

  public TowerButton(int xCoordinate, int yCoordinate, int width, int height, Action action, Tower tower, WaveManager waveManager, Texture texture) {
    super(xCoordinate, yCoordinate, width, height, action, texture);
    this.tower = tower;
    this.waveManager = waveManager;
  }

  public Tower getTower() {
    return tower.getNewTower(xCoordinate, yCoordinate, waveManager);
  }
}
