package data;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static helpers.Artist.*;
import static data.TileGrid.MAP_HEIGHT;

public class UI {
  public static final int UI_HEIGHT = 128;

  private ArrayList<TowerButton> towerButtonList;
  private ArrayList<Button> buttonList;

  private WaveManager waveManager;

  private Texture interfaceBackgroundTexture;
  private Texture interfaceMoneyTexture;
  private Texture interfaceLivesTexture;
  private Texture interfaceWaveTexture;
  private Texture playButtonTexture;
  private Texture pauseButtonTexture;

  private SelectionMenu selectionMenu;

  private Button playPauseButton;

  private boolean pause;

  UI(WaveManager waveManager) {
    this.waveManager = waveManager;
    this.towerButtonList = new ArrayList<>();
    this.buttonList = new ArrayList<>();
    this.interfaceBackgroundTexture = quickLoadTexture("ui_panel");
    this.interfaceMoneyTexture = quickLoadTexture("ui_cash");
    this.interfaceLivesTexture = quickLoadTexture("ui_lives");
    this.interfaceWaveTexture = quickLoadTexture("ui_wave");
    this.playButtonTexture = quickLoadTexture("play_button");
    this.pauseButtonTexture = quickLoadTexture("pause_button");
    this.selectionMenu = null;
    this.pause = true;
    initializeButtons();
  }

  public void draw() {
    drawQuadTex(0, MAP_HEIGHT, interfaceBackgroundTexture);
    towerButtonList.forEach(data.TowerButton::draw);
    buttonList.forEach(data.Button::draw);
    drawQuadTex(10, MAP_HEIGHT + 10, interfaceMoneyTexture);
    drawQuadTex(10, MAP_HEIGHT + 50, interfaceLivesTexture);
    drawQuadTex(10, MAP_HEIGHT + 90, interfaceWaveTexture);
    drawText(65, MAP_HEIGHT + 15, String.valueOf(Player.getMoney()));
    drawText(65, MAP_HEIGHT + 55, String.valueOf(Player.getLives()));
    drawText(65, MAP_HEIGHT + 95, String.valueOf(waveManager.getWaveNumber()));

    if (selectionMenu != null) {
      selectionMenu.draw();
    }
  }

  public Button getSelectedButton(int xCoordinate, int yCoordinate) {
    if (selectionMenu != null) {
      if (checkForSelection(selectionMenu.getSellButton(), xCoordinate, yCoordinate)) {
        return selectionMenu.getSellButton();
      } else if (checkForSelection(selectionMenu.getUpgradeButton(), xCoordinate, yCoordinate)) {
        return selectionMenu.getUpgradeButton();
      }
    }

    for (Button button : buttonList) {
      if (checkForSelection(button, xCoordinate, yCoordinate)) {
        return button;
      }
    }

    for (TowerButton towerButton : towerButtonList) {
      if (checkForSelection(towerButton, xCoordinate, yCoordinate)) {
        return towerButton;
      }
    }

    return null;
  }

  public void setSelectionMenu(Tower selectedTower) {
    if (selectedTower != null) {
      selectionMenu = new SelectionMenu(selectedTower);
    } else {
      selectionMenu = null;
    }
  }

  public void swapPlayPause() {
    if (pause) {
      playPauseButton.setTexture(pauseButtonTexture);
    } else {
      playPauseButton.setTexture(playButtonTexture);
    }

    pause = !pause;
  }

  private boolean checkForSelection(Button button, int xCoordinate, int yCoordinate) {
    if (xCoordinate > button.getXCoordinate() && xCoordinate < button.getXCoordinate() + button.getWidth()) {
      if (yCoordinate > button.getYCoordinate() && yCoordinate < button.getYCoordinate() + button.getHeight()) {
        return true;
      }
    }

    return false;
  }

  private void initializeButtons() {
    playPauseButton = new Button(785, MAP_HEIGHT + 10, 232, 53, Action.PLAY_PAUSE, quickLoadTexture("play_button"));

    towerButtonList.add(new TowerButton(150, MAP_HEIGHT + 10, 80, 110, Action.TOWER, new FastTower(0, 0, waveManager), waveManager, quickLoadTexture("fast_tower_button")));
    towerButtonList.add(new TowerButton(240, MAP_HEIGHT + 10, 80, 110, Action.TOWER, new LaserTower(0, 0, waveManager), waveManager, quickLoadTexture("laser_tower_button")));
    towerButtonList.add(new TowerButton(330, MAP_HEIGHT + 10, 80, 110, Action.TOWER, new HeavyTower(0, 0, waveManager), waveManager, quickLoadTexture("heavy_tower_button")));
    towerButtonList.add(new TowerButton(420, MAP_HEIGHT + 10, 80, 110, Action.TOWER, new HeavyLaserTower(0, 0, waveManager), waveManager, quickLoadTexture("heavy_laser_tower_button")));
    towerButtonList.add(new TowerButton(510, MAP_HEIGHT + 10, 80, 110, Action.TOWER, new BigAssTower(0, 0, waveManager), waveManager, quickLoadTexture("big_ass_tower_button")));
    towerButtonList.add(new TowerButton(600, MAP_HEIGHT + 10, 80, 110, Action.TOWER, new HeavyTower(0, 0, waveManager), waveManager, quickLoadTexture("ion_tower_button")));

    buttonList.add(playPauseButton);
    buttonList.add(new Button(710, MAP_HEIGHT + 10, 232, 53, Action.NEXT_WAVE, quickLoadTexture("next_wave_button")));
    buttonList.add(new Button(860, MAP_HEIGHT + 10, 232, 53, Action.NEXT_WAVE, quickLoadTexture("exit_button")));
  }
}
