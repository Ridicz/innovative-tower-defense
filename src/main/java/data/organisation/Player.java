package data.organisation;

import data.gui.Button;
import data.gui.TowerButton;
import data.gui.UI;
import data.map.Tile;
import data.map.TileGrid;
import data.map.TileType;
import data.towers.Tower;
import data.helpers.Graphics;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.newdawn.slick.opengl.CursorLoader;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static data.helpers.Graphics.*;
import static data.map.Tile.TILE_SIZE;
import static data.gui.UI.UI_HEIGHT;

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

    try {
      this.cursorRegular = CursorLoader.get().getCursor("src/main/java/res/normal_cursor.png", 8, 4);
      this.cursorHovered = CursorLoader.get().getCursor("src/main/java/res/hovered_cursor.png", 8, 4);
    } catch (IOException | LWJGLException e) {
      System.err.println("Cursors not found.");
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

  public void draw() {
    waveManager.draw();

    int x = Mouse.getX();
    int y = Graphics.SCREEN_HEIGHT - Mouse.getY();

    if (placingMode) {
      if (Mouse.getY() > UI_HEIGHT) {
        selectedTower.drawRange(getTile(x, y), selectedTower.getRange());
      }
    } else if (selectedTower != null) {
      selectedTower.drawRange(selectedTower.getXCoordinate(), selectedTower.getYCoordinate(), selectedTower.getRange());
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
      System.err.println("Cursor couldn't be set.");
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
        if (money >= selectedTower.getUpgradePrice()) {
          money -= selectedTower.getUpgradePrice();
          selectedTower.upgrade();
          selectedTower = null;
        }
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
}
