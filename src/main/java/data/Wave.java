package data;

import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;

public class Wave {

  private float timeSinceLastSpawn;
  private float spawnTime;

  private int numberOfEnemiesToSpawn;
  private int numberOfSpawnedEnemies;

  private boolean waveCompleted;

  private Enemy enemyType;

  private CopyOnWriteArrayList<Enemy> enemyList;

  public Wave(float spawnTime, Enemy enemyType, int numberOfEnemiesToSpawn) {
    this.spawnTime = spawnTime;
    this.enemyType = enemyType;
    this.numberOfEnemiesToSpawn = numberOfEnemiesToSpawn;
    this.numberOfSpawnedEnemies = 0;
    this.waveCompleted = false;
    this.timeSinceLastSpawn = Float.MAX_VALUE;
    this.enemyList = new CopyOnWriteArrayList<>();
  }

  public void update() {
    timeSinceLastSpawn += getDelta();

    if (timeSinceLastSpawn > spawnTime && numberOfSpawnedEnemies < numberOfEnemiesToSpawn) {
      spawn();
      ++numberOfSpawnedEnemies;
      timeSinceLastSpawn = 0;
    }

    enemyList.stream().filter(Enemy::isAlive).forEach(Enemy::update);

    enemyList.stream().filter(enemy -> !enemy.isAlive()).forEach(enemyList::remove);

    if (numberOfSpawnedEnemies >= numberOfEnemiesToSpawn && enemyList.isEmpty()) {
      waveCompleted = true;
    }
  }

  public void draw() {
    enemyList.stream().filter(Enemy::isAlive).forEach(Enemy::draw);
  }

  public boolean isWaveCompleted() {
    return waveCompleted;
  }

  public CopyOnWriteArrayList<Enemy> getEnemyList() {
    return enemyList;
  }

  private void spawn() {
    enemyList.add(new Enemy(enemyType.getStartTile(), enemyType.getEndTile(), enemyType.getTileGrid(),
      enemyType.getSpeed(), enemyType.getTexture(), enemyType.getHealth(), enemyType.getReward()));
  }
}
