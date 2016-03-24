package data;

public enum TileType {
  Grass("tile2", true), Dirt("tile1", false), Water("water", false);

  String textureName;
  boolean buildable;

  TileType(String textureName, boolean buildable) {
    this.textureName = textureName;
    this.buildable = buildable;
  }
}
