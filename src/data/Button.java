package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class Button {
  private int width;
  private int height;

  private int xCoordinate;
  private int yCoordinate;

  private Action action;

  private Texture texture;

  public Button(int xCoordinate, int yCoordinate, int width, int height, Action action, Texture texture) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.width = width;
    this.height = height;
    this.action = action;
    this.texture = texture;
  }

  public void draw() {
    drawQuadTex(xCoordinate, yCoordinate, texture);
  }

  public int getXCoordinate() {
    return xCoordinate;
  }

  public int getYCoordinate() {
    return yCoordinate;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Action getAction() {
    return action;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }
}
