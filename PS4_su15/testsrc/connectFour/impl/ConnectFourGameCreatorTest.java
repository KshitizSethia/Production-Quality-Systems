package connectFour.impl;

import org.junit.Test;

public class ConnectFourGameCreatorTest {

  @Test
  public void testCreate2PlayerGame() {
    ConnectFourGameCreator.createTwoPlayerGame("player1", "player2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate2PlayerGame_nullPlayer1() {
    ConnectFourGameCreator.createTwoPlayerGame(null, "player2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate2PlayerGame_nullPlayer2() {
    ConnectFourGameCreator.createTwoPlayerGame("player1", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate2PlayerGame_withSameNames() {
    ConnectFourGameCreator.createTwoPlayerGame("player1", "player1");
  }

  @Test
  public void testCreate1PlayerGame() {
    ConnectFourGameCreator.createSinglePlayerGame("player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate1PlayerGame_nullName() {
    ConnectFourGameCreator.createSinglePlayerGame(null);
  }
}
