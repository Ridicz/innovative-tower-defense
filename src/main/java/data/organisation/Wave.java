package data.organisation;

import data.enemies.Enemy;

import java.util.concurrent.CopyOnWriteArrayList;

import static data.helpers.Clock.*;

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

    if (readyForSpawnNextEnemyFromCurrentWave()) {
      spawn();
      ++numberOfSpawnedEnemies;
      timeSinceLastSpawn = 0;
    }

    enemyList.stream().filter(Enemy::isAlive).forEach(Enemy::update);

    enemyList.stream().filter(enemy -> !enemy.isAlive()).forEach(enemyList::remove);

    waveCompleted = checkIfWaveCompleted();
  }

  private boolean readyForSpawnNextEnemyFromCurrentWave() {
    return (timeSinceLastSpawn > spawnTime && numberOfSpawnedEnemies < numberOfEnemiesToSpawn);
  }

  private boolean checkIfWaveCompleted() {
    return (numberOfSpawnedEnemies >= numberOfEnemiesToSpawn && enemyList.isEmpty());
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
