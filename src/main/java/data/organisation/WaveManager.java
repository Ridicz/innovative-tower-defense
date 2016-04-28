package data.organisation;

import data.enemies.Enemy;
import data.map.Tile;
import data.map.TileGrid;
import data.helpers.Clock;

import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import static data.helpers.Graphics.*;

public class WaveManager {
  private int waveNumber;
  private double timer;

  private Wave wave;

  private Tile startTile;
  private Tile endTile;

  private TileGrid tileGrid;

  private Stack<Wave> waveStack;

  public WaveManager(Tile startTile, Tile endTile, TileGrid tileGrid) {
    this.startTile = startTile;
    this.endTile = endTile;
    this.tileGrid = tileGrid;
    this.waveStack = new Stack<>();
    this.waveNumber = 1;
    generateWaves();
    this.wave = waveStack.pop();
    this.timer = 0;
  }

  public void update() {
    if (!wave.isWaveCompleted()) {
      wave.update();
    } else if (timer >= 10) {
      wave = waveStack.pop();
      wave.update();
      ++waveNumber;
      timer = 0;
    } else {
      timer += Clock.getDelta();
    }
  }

  public void draw() {
    wave.draw();

    if (wave.isWaveCompleted()) {
      String s = "Time till next wave: ";
      int time = 10 - (int) timer;
      drawQuadTex(300, 0, quickLoadTexture("next_wave_panel"));
      drawText(370, 15, s + String.valueOf(time));
    }
  }

  public CopyOnWriteArrayList<Enemy> getEnemyList() {
    return wave.getEnemyList();
  }

  public int getWaveNumber() {
    return waveNumber;
  }

  public void endWave() {
    if (wave.isWaveCompleted()) {
      timer = 10;
    }

    //Maybe TODO
  }

  private void generateWaves() {
    Enemy enemy = new Enemy(startTile, endTile, tileGrid, 2, quickLoadTexture("tank"), 500, 50);
    waveStack.push(new Wave(1.5f, enemy, 15));

    enemy = new Enemy(startTile, endTile, tileGrid, 1, quickLoadTexture("tank"), 400, 20);
    waveStack.push(new Wave(2, enemy, 20));

    enemy = new Enemy(startTile, endTile, tileGrid, 1, quickLoadTexture("tank"), 300, 20);
    waveStack.push(new Wave(2, enemy, 20));

    enemy = new Enemy(startTile, endTile, tileGrid, 1, quickLoadTexture("tank"), 200, 30);
    waveStack.push(new Wave(2, enemy, 20));

    enemy = new Enemy(startTile, endTile, tileGrid, 1, quickLoadTexture("tank"), 100, 20);
    waveStack.push(new Wave(2, enemy, 20));
  }
}
