import data.Player;
import data.Tile;
import data.TileGrid;
import data.TileType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
  Player player = new Player(new Tile(0, 0, TileType.Grass), new Tile(10, 10, TileType.Grass), new TileGrid(new int[0][0]));

//  @Before
//  public void setUp() throws Exception {
//
//    int money = 100;
//  }

  @Test
  public void testAddMoney() throws Exception {
    Player.addMoney(100);
    Assert.assertEquals("Money after addition should equals money", 110, Player.getMoney());
  }
}
