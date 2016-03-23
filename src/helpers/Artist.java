package helpers;

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
import static data.TileGrid.MAP_HEIGHT;
import static data.UI.UI_HEIGHT;

public class Artist {
  public static final int SCREEN_WIDTH = 960;
  public static final int SCREEN_HEIGHT = MAP_HEIGHT + UI_HEIGHT;

  static Font awtFont;
  static TrueTypeFont font;

  public static void beginSession() {
    Display.setTitle("The Game");

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

    awtFont = new Font("Trebuchet MS", Font.PLAIN, 18);
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

  @SuppressWarnings("SuspiciousNameCombination")
  public static void drawLaserBeam(float xCoordinate, float yCoordinate, float width, float height, float angle,
                                   double alpha, Texture startTexture, Texture middleTexture, Texture endTexture) {
    glColor4d(1, 1, 1, alpha);

//    startTexture.bind();
//    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//    glTranslatef(xCoordinate, yCoordinate, 0);
//    glRotatef(angle, 0, 0, 1);
//    glBegin(GL_QUADS);
//    glTexCoord2f(0, 0);
//    glVertex2f(0, 0);
//    glTexCoord2f(1, 0);
//    glVertex2f(width, 0);
//    glTexCoord2f(1, 1);
//    glVertex2f(width, width);
//    glTexCoord2f(0, 1);
//    glVertex2f(0, width);
//    glEnd();
//    glLoadIdentity();
//
//    middleTexture.bind();
//    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//    glTranslatef(xCoordinate, yCoordinate, 0);
//    glRotatef(angle, 0, 0, 1);
//    glBegin(GL_QUADS);
//    glTexCoord2f(0, 0);
//    glVertex2f(0, width);
//    glTexCoord2f(1, 0);
//    glVertex2f(width, width);
//    glTexCoord2f(1, 1);
//    glVertex2f(width, height);
//    glTexCoord2f(0, 1);
//    glVertex2f(0, height);
//    glEnd();
//    glLoadIdentity();
//
//    endTexture.bind();
//    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//    glTranslatef(xCoordinate, yCoordinate, 0);
//    glRotatef(angle, 0, 0, 1);
//    glBegin(GL_QUADS);
//    glTexCoord2f(0, 0);
//    glVertex2f(0, height);
//    glTexCoord2f(1, 0);
//    glVertex2f(width, height);
//    glTexCoord2f(1, 1);
//    glVertex2f(width, height + width);
//    glTexCoord2f(0, 1);
//    glVertex2f(0, height + width);
//    glEnd();
//    glLoadIdentity();

    int gap = 8;

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

    startTexture.bind();
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glTranslatef(xCoordinate, yCoordinate, 0);
    glRotatef(angle, 0, 0, 1);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(gap, 0);
    glTexCoord2f(1, 0);
    glVertex2f(width + gap, 0);
    glTexCoord2f(1, 1);
    glVertex2f(width + gap, width);
    glTexCoord2f(0, 1);
    glVertex2f(gap, width);
    glEnd();
    glLoadIdentity();

    middleTexture.bind();
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glTranslatef(xCoordinate, yCoordinate, 0);
    glRotatef(angle, 0, 0, 1);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(gap, width);
    glTexCoord2f(1, 0);
    glVertex2f(width + gap, width);
    glTexCoord2f(1, 1);
    glVertex2f(width + gap, height);
    glTexCoord2f(0, 1);
    glVertex2f(gap, height);
    glEnd();
    glLoadIdentity();

    endTexture.bind();
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glTranslatef(xCoordinate, yCoordinate, 0);
    glRotatef(angle, 0, 0, 1);
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(gap, height);
    glTexCoord2f(1, 0);
    glVertex2f(width + gap, height);
    glTexCoord2f(1, 1);
    glVertex2f(width + gap, height + width);
    glTexCoord2f(0, 1);
    glVertex2f(gap, height + width);
    glEnd();
    glLoadIdentity();

    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glColor4d(1, 1, 1, 1);
  }

//  public static void drawHollowCircle(int xCoordinate, int yCoordinate, int radius) {
//    int lineAmount = 100;
//    double twicePi = 2 * Math.PI;
//
//    glBegin(GL_LINE_LOOP);
//    glColor3d(1, 0, 1);
//    for(int i = 0; i <= lineAmount; i++) {
//      glVertex2d(
//        xCoordinate + (radius * Math.cos(i * twicePi / lineAmount)),
//        yCoordinate + (radius * Math.sin(i * twicePi / lineAmount))
//      );
//    }
//
//    glEnd();
//
//    glColor3d(1, 1, 1);
//  }

  public static void drawText(int xCoordinate, int yCoordinate, String text) {
    font.drawString(xCoordinate, yCoordinate, text, new Color(185, 185, 185));
    glColor3d(1, 1, 1);
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

  public static Texture quickLoadTexture(String name) {
    return loadTexture("res/" + name + ".png", "PNG");
  }
}








