package data.enemies;

import org.newdawn.slick.opengl.Texture;

import static data.helpers.Graphics.*;

class HealthBar {
  private static final int WIDTH = 64;
  private static final int HEIGHT = 8;

  private int xCoordinate;
  private int yCoordinate;
  private int healthPercentage;

  private Texture backgroundTexture;
  private Texture foregroundTexture;
  private Texture borderTexture;

  HealthBar(int xCoordinate, int yCoordinate) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate - 30;
    this.healthPercentage = 100;
    this.backgroundTexture = quickLoadTexture("healthbar_background");
    this.foregroundTexture = quickLoadTexture("healthbar_foreground");
    this.borderTexture = quickLoadTexture("healthbar_border");
  }

  public void draw() {
    drawQuadTex(xCoordinate, yCoordinate, WIDTH, HEIGHT, backgroundTexture);
    drawQuadTex(xCoordinate, yCoordinate, WIDTH, HEIGHT, borderTexture);
    drawQuadTex(xCoordinate, yCoordinate, (int) (WIDTH * 1.0) * healthPercentage / 100, HEIGHT, foregroundTexture);
  }

  public void setXCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public void setYCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate - 10;
  }

  public void setHealthPercentage(int healthPercentage) {
    this.healthPercentage = healthPercentage;
  }
}
