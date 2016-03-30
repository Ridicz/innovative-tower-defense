package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static data.TileGrid.*;

public class SelectionMenu {
  private int xCoordinate;
  private int yCoordinate;

  private Texture selectionMenuTexture;

  private Button sellButton;
  private Button upgradeButton;

  private Tower selectedTower;

  public SelectionMenu(Tower selectedTower) {
    this.selectedTower = selectedTower;
    this.selectionMenuTexture = quickLoadTexture("selection_menu");

    if (selectedTower.getXCoordinate() > MAP_WIDTH - selectionMenuTexture.getImageWidth() ) {
      this.xCoordinate = selectedTower.getXCoordinate() - selectionMenuTexture.getImageWidth();
    } else {
      this.xCoordinate = selectedTower.getXCoordinate() + 64;
    }

    if (selectedTower.getYCoordinate() < selectionMenuTexture.getImageHeight()) {
      this.yCoordinate = selectedTower.getYCoordinate();
    } else {
      this.yCoordinate = selectedTower.getYCoordinate() - 200;
    }

    sellButton = new Button(xCoordinate + 10, yCoordinate + 170, 80, 20, Action.SELL, quickLoadTexture("sell_button"));
    upgradeButton = new Button(xCoordinate + 100, yCoordinate + 170, 80, 20, Action.UPGRADE, quickLoadTexture("upgrade_button"));
  }

  public void draw() {
    drawQuadTex(xCoordinate, yCoordinate, selectionMenuTexture);
    sellButton.draw();
    upgradeButton.draw();
    drawText(xCoordinate + 10, yCoordinate + 10, "Damage: " + selectedTower.getDamage());
    drawText(xCoordinate + 10, yCoordinate + 35, "Reload: " + selectedTower.getReloadTime());
    drawText(xCoordinate + 10, yCoordinate + 60, "Range: " + selectedTower.getRange());
    drawText(xCoordinate + 10, yCoordinate + 85, "DPS: " + (int) (selectedTower.getDamage() / selectedTower.getReloadTime()));
  }

  public Button getSellButton() {
    return sellButton;
  }

  public Button getUpgradeButton() {
    return upgradeButton;
  }
}
