package data.helpers;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static data.map.TileGrid.MAP_HEIGHT;
import static data.map.TileGrid.MAP_WIDTH;
import static data.gui.UI.UI_HEIGHT;

public class Graphics {
  public static final int SCREEN_WIDTH = MAP_WIDTH;
  public static final int SCREEN_HEIGHT = MAP_HEIGHT + UI_HEIGHT;

  private static TrueTypeFont font;

  public static void beginSession() {
    try {
      Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
      Display.create();
    } catch (LWJGLException e) {
      e.printStackTrace();
    }

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    final Font awtFont = new Font("Trebuchet MS", Font.PLAIN, 18);
    font = new TrueTypeFont(awtFont, true);
  }

  public static void drawQuadTex(float xCoordinate, float yCoordinate, float width, float height, Texture texture) {
    texture.bind();
    glTranslatef(xCoordinate, yCoordinate, 0);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(0, 0);
    glTexCoord2f(1, 0);
    glVertex2f(width, 0);
    glTexCoord2f(1, 1);
    glVertex2f(width, height);
    glTexCoord2f(0, 1);
    glVertex2f(0, height);
    glEnd();
    glLoadIdentity();
  }

  public static void drawQuadTex(float xCoordinate, float yCoordinate, Texture texture) {
    texture.bind();
    glTranslatef(xCoordinate, yCoordinate, 0);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(0, 0);
    glTexCoord2f(1, 0);
    glVertex2f(texture.getTextureWidth(), 0);
    glTexCoord2f(1, 1);
    glVertex2f(texture.getTextureWidth(), texture.getTextureHeight());
    glTexCoord2f(0, 1);
    glVertex2f(0, texture.getTextureHeight());
    glEnd();
    glLoadIdentity();
  }

  public static void drawQuadTexRotated(float xCoordinate, float yCoordinate, float width, float height, float angle, Texture texture) {
    texture.bind();
    glTranslatef(xCoordinate + width / 2, yCoordinate + height / 2, 0);
    glRotatef(angle, 0, 0, 1);
    glTranslatef(- width / 2, - height / 2, 0);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(0, 0);
    glTexCoord2f(1, 0);
    glVertex2f(texture.getTextureWidth(), 0);
    glTexCoord2f(1, 1);
    glVertex2f(texture.getTextureWidth(), texture.getTextureHeight());
    glTexCoord2f(0, 1);
    glVertex2f(0, texture.getTextureHeight());
    glEnd();
    glLoadIdentity();
  }

  public static void drawQuadTexRotated2(float xCoordinate, float yCoordinate, float width, float height, float angle, Texture texture) {
    texture.bind();
    glTranslatef(xCoordinate + width / 2, yCoordinate + height / 2, 0);
    glRotatef(angle, 0, 0, 1);
    glTranslatef(-width / 2, -height / 2, 0);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(0, 0);
    glTexCoord2f(1, 0);
    glVertex2f(width, 0);
    glTexCoord2f(1, 1);
    glVertex2f(width, height);
    glTexCoord2f(0, 1);
    glVertex2f(0, height);
    glEnd();
    glLoadIdentity();
  }

  public static void drawSingleLaserBeam(float xCoordinate, float yCoordinate, float width, float height, float angle,
                                         double alpha, Texture startTexture, Texture middleTexture, Texture endTexture) {
    drawLaserBeam(xCoordinate, yCoordinate, width, height, angle, alpha, startTexture, middleTexture, endTexture, 0);
  }

  public static void drawDoubleLaserBeam(float xCoordinate, float yCoordinate, float width, float height, float angle,
                                         double alpha, Texture startTexture, Texture middleTexture, Texture endTexture) {
    drawLaserBeam(xCoordinate, yCoordinate, width, height, angle, alpha, startTexture, middleTexture, endTexture, - 8);
    drawLaserBeam(xCoordinate, yCoordinate, width, height, angle, alpha, startTexture, middleTexture, endTexture, 8);
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private static void drawLaserBeam(float xCoordinate, float yCoordinate, float width, float height, float angle,
                                   double alpha, Texture startTexture, Texture middleTexture, Texture endTexture, int gap) {
    glColor4d(1, 1, 1, alpha);

    startTexture.bind();
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glTranslatef(xCoordinate, yCoordinate, 0);
    glRotatef(angle, 0, 0, 1);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(- gap, 0);
    glTexCoord2f(1, 0);
    glVertex2f(width - gap, 0);
    glTexCoord2f(1, 1);
    glVertex2f(width - gap, width);
    glTexCoord2f(0, 1);
    glVertex2f(- gap, width);
    glEnd();
    glLoadIdentity();

    middleTexture.bind();
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glTranslatef(xCoordinate, yCoordinate, 0);
    glRotatef(angle, 0, 0, 1);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(- gap, width);
    glTexCoord2f(1, 0);
    glVertex2f(width - gap, width);
    glTexCoord2f(1, 1);
    glVertex2f(width - gap, height);
    glTexCoord2f(0, 1);
    glVertex2f(- gap, height);
    glEnd();
    glLoadIdentity();

    endTexture.bind();
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glTranslatef(xCoordinate, yCoordinate, 0);
    glRotatef(angle, 0, 0, 1);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(- gap, height);
    glTexCoord2f(1, 0);
    glVertex2f(width - gap, height);
    glTexCoord2f(1, 1);
    glVertex2f(width - gap, height + width);
    glTexCoord2f(0, 1);
    glVertex2f(- gap, height + width);
    glEnd();
    glLoadIdentity();

    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glColor4d(1, 1, 1, 1);
  }

  public static void drawText(int xCoordinate, int yCoordinate, String text) {
    font.drawString(xCoordinate, yCoordinate, text, new Color(185, 185, 185));
    glColor3d(1, 1, 1);
  }

  public static Texture quickLoadTexture(String name) {
    return loadTexture("src/main/java/res/" + name + ".png", "PNG");
  }

  private static Texture loadTexture(String inputTexturePath, String fileType) {
    Texture inputTexture = null;

    InputStream in = ResourceLoader.getResourceAsStream(inputTexturePath);

    try {
      inputTexture = TextureLoader.getTexture(fileType, in);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return inputTexture;
  }
}








