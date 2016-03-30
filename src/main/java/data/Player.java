package data;

import helpers.Artist;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.Texture;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Artist.*;
import static data.Tile.TILE_SIZE;
import static data.UI.UI_HEIGHT;

public class Player {
  private static int money;
  private static int lives;

  private boolean mouseClicked;
  private boolean placingMode;
  private boolean paused;
  private boolean buttonHovered;

  private Cursor cursorRegular;
  private Cursor cursorHovered;

  private TileGrid tileGrid;

  private UI userInterface;

  private WaveManager waveManager;

  private Tower selectedTower;

  private CopyOnWriteArrayList<Tower> towerList;

  private Texture rangeTexture;

  public Player(Tile startTile, Tile endTile, TileGrid tileGrid) {
    money = 1500;
    lives = 4;
    this.mouseClicked = false;
    this.tileGrid = tileGrid;
    this.waveManager = new WaveManager(startTile, endTile, tileGrid);
    this.towerList = new CopyOnWriteArrayList<>();
    this.userInterface = new UI(waveManager);
    this.placingMode = false;
    this.paused = true;
    this.selectedTower = null;
    this.buttonHovered = false;
    this.rangeTexture = quickLoadTexture("range");

    try {
      this.cursorRegular = CursorLoader.get().getCursor("src/main/java/res/normal_cursor.png", 8, 4);
      this.cursorHovered = CursorLoader.get().getCursor("src/main/java/res/hovered_cursor.png", 8, 4);
    } catch (IOException | LWJGLException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    checkForMouseInput();

    if (lives <= 0 || paused) {
      return;
    }

    towerList.forEach(Tower::update);
    waveManager.update();
  }

  public void draw() {
    waveManager.draw();

    if (placingMode) {
      if (Mouse.getY() > UI_HEIGHT) {
        drawRange();
      }
    } else if (selectedTower != null) {
      drawRange(selectedTower.getXCoordinate(), selectedTower.getYCoordinate());
    }

    towerList.forEach(Tower::draw);
    userInterface.draw();

    try {
      if (buttonHovered) {
        Mouse.setNativeCursor(cursorHovered);
      } else {
        Mouse.setNativeCursor(cursorRegular);
      }
    } catch (LWJGLException e) {
      e.printStackTrace();
    }
  }

  public static void addMoney(int money) {
    Player.money += money;
  }

  public static void decreaseLives() {
    --Player.lives;
  }

  public static int getMoney() {
    return money;
  }

  public static int getLives() {
    return lives;
  }

  private void checkForMouseInput() {
    int x = Mouse.getX();
    int y = SCREEN_HEIGHT - Mouse.getY();

    Button selectedButton = userInterface.getSelectedButton(x, y);

    buttonHovered = (selectedButton != null);

    if (Mouse.isButtonDown(0) && !mouseClicked) {
      userInterface.setSelectionMenu(null);

      if (buttonHovered) {
        handleAction(selectedButton);
      } else if (Mouse.getY() > UI_HEIGHT) {
        Tile pickedTile = getTile(x, y);

        if (placingMode) {
          placeTower(pickedTile);
        } else {
          selectedTower = pickedTile.getTower();
          userInterface.setSelectionMenu(selectedTower);
        }
      }
    }

    mouseClicked = Mouse.isButtonDown(0);
  }

  private void handleAction(Button buttonClicked) {
    switch (buttonClicked.getAction()) {
      case TOWER:
        selectTower(buttonClicked);
        break;

      case PLAY_PAUSE:
        paused = !paused;
        userInterface.swapPlayPause();
        break;

      case SELL:
        if (selectedTower != null) {
          money += selectedTower.getPrice() * 0.75;
          towerList.remove(selectedTower);
          selectedTower.getTilePlaced().setTower(null);
          selectedTower = null;
        }
        break;

      case NEXT_WAVE:
        waveManager.endWave();
        break;

      case UPGRADE:

        break;
    }
  }

  private void selectTower(Button buttonClicked) {
    TowerButton towerButton = (TowerButton) buttonClicked;

    selectedTower = towerButton.getTower();

    if (selectedTower == null) {
      placingMode = false;
    } else if (selectedTower.getPrice() > money) {
      selectedTower = null;
      placingMode = false;
    } else {
      placingMode = true;
    }
  }

  private void placeTower(Tile pickedTile) {
    if (pickedTile.getTileType() == TileType.Grass) {
      if (pickedTile.getTower() == null) {
        selectedTower.setXCoordinate(pickedTile.getXCoordinate());
        selectedTower.setYCoordinate(pickedTile.getYCoordinate());
        selectedTower.setTilePlaced(pickedTile);
        pickedTile.setTower(selectedTower);
        towerList.add(selectedTower);
        money -= selectedTower.getPrice();
      }
    }

    selectedTower = null;
    placingMode = false;
  }

  private Tile getTile(int xCoordinate, int yCoordinate) {
    return tileGrid.getTile(xCoordinate / TILE_SIZE, yCoordinate / TILE_SIZE);
  }

  private void drawRange() {
    int x = Mouse.getX();
    int y = Artist.SCREEN_HEIGHT - Mouse.getY();

    Tile tileHovered = getTile(x, y);

    drawRange(tileHovered.getXCoordinate(), tileHovered.getYCoordinate());
  }

  private void drawRange(int xCoordinate, int yCoordinate) {
    int range = selectedTower.getRange();

    drawQuadTex(xCoordinate - range + 32, yCoordinate - range + 32, range * 2, range * 2, rangeTexture);
  }
}
