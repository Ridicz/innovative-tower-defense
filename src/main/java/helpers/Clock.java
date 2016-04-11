package helpers;

import org.lwjgl.Sys;

public class Clock {
  private static boolean paused = false;

  private static long lastFrame;
  //private static long totalTime;

  private static float delta = 0;
  private static float multiplier = 1;



  public static float getDelta() {
    if (paused)
      return 0;

    return delta * multiplier;
  }

  public static void update() {
    delta = countDelta();
    //totalTime += delta;
  }

  private static float countDelta() {
    long currentTime = getTime();
    int delta = (int) (currentTime - lastFrame);
    lastFrame = currentTime;

    return delta * 0.001f;
  }

  public static void changeMultiplier(int newMultiplier) {
    multiplier = newMultiplier;
  }

  public static void pause() {
    paused = !paused;
  }

  private static long getTime() {
    return Sys.getTime() * 1000 / Sys.getTimerResolution();
  }
}
