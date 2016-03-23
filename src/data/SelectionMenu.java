package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class SelectionMenu {
  private Texture selectionMenu;

  private Button sellButton;
  private Button upgradeButton;

  private Tower selectedTower;

  public SelectionMenu(Tower selectedTower) {
    this.selectedTower = selectedTower;
    this.selectionMenu = quickLoadTexture("selection_menu");
    sellButton = new Button(selectedTower.getXCoordinate() + 74, selectedTower.getYCoordinate() - 30, 80, 20, Action.SELL, quickLoadTexture("sell_button"));
    upgradeButton = new Button(selectedTower.getXCoordinate() + 164, selectedTower.getYCoordinate() - 30, 80, 20, Action.UPGRADE, quickLoadTexture("upgrade_button"));
  }

  public void draw() {
    drawQuadTex(selectedTower.getXCoordinate() + 64, selectedTower.getYCoordinate() - 200, selectionMenu);
    sellButton.draw();
    upgradeButton.draw();
  }

  public Button getSellButton() {
    return sellButton;
  }

  public Button getUpgradeButton() {
    return upgradeButton;
  }
}
