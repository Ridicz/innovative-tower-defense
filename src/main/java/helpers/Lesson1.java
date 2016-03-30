package helpers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Lesson1 {
  private static IntBuffer buffer = BufferUtils.createIntBuffer(1);

  private static IntBuffer source = BufferUtils.createIntBuffer(1);

  private static FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

  private static FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

  private static FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

  private static FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

  private static FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();

  public static void killALData() {
    AL10.alDeleteSources(source);
    AL10.alDeleteBuffers(buffer);
    AL.destroy();
  }

  public static void execute() {
    if (!AL.isCreated()) {
      try {
        AL.create();
      } catch (LWJGLException le) {
        le.printStackTrace();
        return;
      }
      AL10.alGetError();
    }

    if(loadALData() == AL10.AL_FALSE) {
      System.out.println("Error loading data.");
      return;
    }

    setListenerValues();

    AL10.alSourcePlay(source.get(0));
  }

  private static  int loadALData() {
    AL10.alGenBuffers(buffer);

    if(AL10.alGetError() != AL10.AL_NO_ERROR)
      return AL10.AL_FALSE;

    WaveData waveFile = WaveData.create("res/sfx.wav");

    AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
    waveFile.dispose();

    AL10.alGenSources(source);

    if (AL10.alGetError() != AL10.AL_NO_ERROR)
      return AL10.AL_FALSE;

    AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
    AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f          );
    AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f          );
    AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos     );
    AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel     );

    if (AL10.alGetError() == AL10.AL_NO_ERROR)
      return AL10.AL_TRUE;

    return AL10.AL_FALSE;
  }

  private static void setListenerValues() {
    AL10.alListener(AL10.AL_POSITION,    listenerPos);
    AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
  }
}
