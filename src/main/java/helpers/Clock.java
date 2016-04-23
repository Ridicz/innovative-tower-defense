package helpers;

public class Clock {
  private static boolean paused = false;

  private static long lastFrame;

  private static float delta = 0;

  public static float getDelta() {
    if (paused)
      return 0;

    return delta;
  }

  public static void update() {
    delta = countDelta();
  }

  private static float countDelta() {
    long currentTime = getTime();
    float currentDelta = currentTime - lastFrame;
    lastFrame = currentTime;

    return currentDelta * 0.001f;
  }

  public static void pause() {
    paused = !paused;
  }

  public static long getTime() {
    return System.nanoTime() / 1000000;
  }
}
