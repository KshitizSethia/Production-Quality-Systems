package connectFour.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourObserver;
import connectFour.api.IConnectFourPlayer;
import connectFour.test.Notif;
import connectFour.test.TestObserver;
import connectFour.test.TestPlayer;

public class ConnectFourModelTest {

  @Rule
  public ExpectedException  thrown = ExpectedException.none();

  private IConnectFourModel model;

  @Before
  public void setUp() throws Exception {
    model = ConnectFourGameCreator.createModelWithoutPlayers();
    // thrown= ExpectedException.none();
  }

  public String addTwoPlayersToModel() {
    String firstPlayerName = "1";
    model.addObserver(new TestPlayer(model, firstPlayerName));
    model.addObserver(new TestPlayer(model, "2"));
    return firstPlayerName;
  }

  public void playMove(int column) {
    Move move = getMove(column);
    model.notifyMovePlayed(move);
  }

  public Move getMove(int column) {
    int row = model.getGameState().getRow(column);
    Move move = new Move(row, column, model.whoHasNextChance());
    return move;
  }

  @Test
  public void testAddRemoveObserver_addTwoPlayersOneObserver_removeAll() {

    IConnectFourPlayer player1 = new TestPlayer(model, "1");
    IConnectFourPlayer player2 = new TestPlayer(model, "2");
    IConnectFourObserver obs = new TestObserver(model);
    model.addObserver(player1);
    model.addObserver(player2);
    model.addObserver(obs);

    model.removeObserver(player1);
    model.removeObserver(player2);
    model.removeObserver(obs);
  }

  @Test
  public void testAddObserver_addThreePlayers() {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Max number of players added");

    addTwoPlayersToModel();
    model.addObserver(new TestPlayer(model, "3"));
  }

  @Test
  public void testAddObserver_addPlayerWhenGameOn() {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Cannot add player when game is in progress.");

    String firstPlayer = addTwoPlayersToModel();
    model.startGame(firstPlayer);
    model.addObserver(new TestPlayer(model, "3"));
  }

  @Test
  public void testNotifyMovePlayed_addObserverWhenGameOn_checkNotification() {
    String firstChance = addTwoPlayersToModel();
    model.startGame(firstChance);
    TestObserver obs = new TestObserver(model);
    model.addObserver(obs);

    // check that no notifications have been received yet
    assertTrue(obs.getNotifs().size() == 0);

    // trigger notifications
    playMove(0);

    // check notification received
    List<Notif> notifs = obs.getNotifs();
    assertTrue(notifs.size() == 1);
    assertTrue(notifs.get(0) == Notif.movePlayed);
  }

  @Test
  public void testAddObserver_addPlayersWithSameName() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Player name already taken");

    model.addObserver(new TestPlayer(model, "1"));
    model.addObserver(new TestPlayer(model, "1"));
  }

  @Test
  public void testRemoveObserver_removeWhenEmpty() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Unable to remove");

    model.removeObserver(new TestObserver(model));
  }

  @Test
  public void testRemoveObserver_whenGameOn() {
    String name = addTwoPlayersToModel();
    TestObserver obs = new TestObserver(model);
    model.addObserver(obs);
    model.startGame(name);

    // see initial notif length
    int startLength = obs.getNotifs().size();
    // remove observer
    model.removeObserver(obs);
    // trigger more notifications
    playMove(0);
    // ensure new notifications have not been received
    assertEquals(obs.getNotifs().size(), startLength);
  }

  @Test
  public void testAddRemoveObserver_addOnePlayer_remove_addAgain() {
    TestPlayer player = new TestPlayer(model, "name");
    model.addObserver(player);
    model.removeObserver(player);
    model.addObserver(player);
  }

  @Ignore
  @Test
  public void
      testAddRemoveObserver_addOneObserver_remove_addAgain_checkNotify() {
    // testRemoveObserver_whenGameOn covers this
  }

  @Ignore
  @Test
  public void testRemoveObserver_addOneObserver_removeAnother() {
    // TODO check notif part
  }

  @Test
  public void testNotifyGameStarted_startWithOnePlayer() {
    model.addObserver(new TestPlayer(model, "test"));

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Cannot start game, all players not added");

    model.notifyGameStarted("test");
  }

  @Test
  public void testNotifyGameStarted_startWhenGameIsOn() {
    String name = addTwoPlayersToModel();
    model.startGame(name);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is in progress");

    model.notifyGameStarted(name);
  }

  @Test
  public void testNotifyGameStarted_withWrongPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player name not correct");
    model.notifyGameStarted("wrong name");
  }

  @Test
  public void testNotifyGameStarted_withNullPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player name not correct");
    model.notifyGameStarted(null);
  }

  @Test
  public void testNotifyGameStarted_withEmptyPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player name not correct");
    model.notifyGameStarted("");
  }

  @Test
  public void testNotifyGameStarted_TwoPlayersOneObserver_checkPlayerID() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");
    TestObserver obs = new TestObserver(model);

    model.addObserver(player1);
    model.addObserver(player2);
    model.addObserver(obs);

    model.notifyGameStarted("1");

    assertTrue(player1.getPlayerID() == Player.player1
        || player1.getPlayerID() == Player.player2);
    assertTrue(player2.getPlayerID() == Player.player1
        || player2.getPlayerID() == Player.player2);
    assertTrue(obs.getPlayerID() == Player.none);
  }

  @Test
  public void testNotifyGameStarted_TwoPlayersOneObserver_checkFirstChance() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");
    TestObserver obs = new TestObserver(model);

    model.addObserver(player1);
    model.addObserver(player2);
    model.addObserver(obs);

    model.notifyGameStarted("1");

    assertEquals(model.whoHasNextChance(), player1.getPlayerID());
  }

  @Test
  public void
      testNotifyMovePlayed_TwoPlayersOneObserver_TwoMoves_checkNotifications() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");
    TestObserver obs = new TestObserver(model);

    model.addObserver(player1);
    model.addObserver(player2);
    model.addObserver(obs);
    assertEquals(player1.getNotifs().size(), 0);
    assertEquals(player2.getNotifs().size(), 0);
    assertEquals(obs.getNotifs().size(), 0);

    model.notifyGameStarted("1");
    assertEquals(player1.getNotifs().size(), 1);
    assertEquals(player2.getNotifs().size(), 1);
    assertEquals(obs.getNotifs().size(), 1);
    assertEquals(player1.getNotifs().get(0), Notif.started);
    assertEquals(player2.getNotifs().get(0), Notif.started);
    assertEquals(obs.getNotifs().get(0), Notif.started);

    playMove(0);
    assertEquals(player1.getNotifs().get(1), Notif.movePlayed);
    assertEquals(player2.getNotifs().get(1), Notif.movePlayed);
    assertEquals(obs.getNotifs().get(1), Notif.movePlayed);

    playMove(1);
    assertEquals(player1.getNotifs().get(2), Notif.movePlayed);
    assertEquals(player2.getNotifs().get(2), Notif.movePlayed);
    assertEquals(obs.getNotifs().get(2), Notif.movePlayed);
  }

  @Test
  public void testNotifyMovePlayed_whenGameNotStarted() {
    addTwoPlayersToModel();

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is not in progress");

    int col = 0;
    int row = model.getGameState().getRow(col);
    model.notifyMovePlayed(new Move(row, col, Player.player1));
  }

  @Test
  public void testNotifyMovePlayed_moveByObserver() {
    String name = addTwoPlayersToModel();
    TestObserver obs = new TestObserver(model);
    model.addObserver(obs);
    model.startGame(name);

    Move observerMove = new Move(GameConfig.numRows - 1, 0, obs.getPlayerID());

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Move can only be played by a player");
    model.notifyMovePlayed(observerMove);
  }

  @Test
  public void testNotifyMovePlayed_twoPlayers_twoMoves_checkSwitchingOfChance() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());

    assertEquals(model.whoHasNextChance(), player1.getPlayerID());

    playMove(0);
    assertEquals(model.whoHasNextChance(), player2.getPlayerID());

    playMove(0);
    assertEquals(model.whoHasNextChance(), player1.getPlayerID());
  }

  @Test
  public void testNotifyMovePlayed_moveOutOfChance() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());

    int col = 0;
    int row = GameConfig.numRows - 1;
    Move outOfChanceMove = new Move(row, col, player2.getPlayerID());

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Moved without chance");
    model.notifyMovePlayed(outOfChanceMove);
  }

  @Test
  public void testNotifyMovePlayed_moveWithNullPlayerID() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());

    int col = 0;
    int row = GameConfig.numRows - 1;
    Move moveWithNullPlayerID = new Move(row, col, null);

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Move.getPlayedBy() is null");
    model.notifyMovePlayed(moveWithNullPlayerID);
  }

  @Test
  public void testNotifyMovePlayedAndNotifyGameFinished_whenGameWon() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());

    // player1 plays on col 0 and player2 plays on column 1 three times each
    for (int round = 0; round < 3; round++) {
      playMove(0);
      playMove(1);
    }

    int playerNotifCount = player1.getNotifs().size();
    assertEquals(player2.getNotifs().size(), playerNotifCount);

    // play winning move and check notifs
    playMove(0);
    assertEquals(player1.getNotifs().size(), playerNotifCount + 2);
    assertEquals(player2.getNotifs().size(), playerNotifCount + 2);

    assertEquals(player1.getNotifFromEnd(-1), Notif.finished);
    assertEquals(player1.getNotifFromEnd(-2), Notif.movePlayed);

    assertEquals(player2.getNotifFromEnd(-1), Notif.finished);
    assertEquals(player2.getNotifFromEnd(-2), Notif.movePlayed);

    // check winner
    assertEquals(player1.getWinner(), player1.getPlayerID());
    assertEquals(player2.getWinner(), player1.getPlayerID());

    // check gameOn
    assertEquals(model.isGameOn(), false);
  }

  @Test
  public void testNotifyMovePlayedAndNotifyGameFinished_whenGameDraw() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());
    assertFalse(model.getGameState().isFull());

    playDrawSequence();

    assertEquals(player1.getNotifFromEnd(-1), Notif.finished);
    assertEquals(player1.getNotifFromEnd(-2), Notif.movePlayed);

    assertEquals(player2.getNotifFromEnd(-1), Notif.finished);
    assertEquals(player2.getNotifFromEnd(-2), Notif.movePlayed);

    // check winner
    assertEquals(player1.getWinner(), Player.none);
    assertEquals(player2.getWinner(), Player.none);

    // check game ended
    assertFalse(model.isGameOn());
    assertTrue(model.getGameState().isFull());
  }

  /**
   * Play the following sequence on a 6 row x 7 column board where
   * <p>
   * 1: player 1
   * <p>
   * 2: player 2
   * 
   * <p>
   * 1 2 1 2 1 2 2
   * <p>
   * 1 2 1 2 1 2 1
   * <p>
   * 2 1 2 1 2 1 2
   * <p>
   * 2 1 2 1 2 1 1
   * <p>
   * 1 2 1 2 1 2 2
   * <p>
   * 1 2 1 2 1 2 1
   */
  private void playDrawSequence() {
    // assert size
    assertEquals(GameConfig.numRows, 6);
    assertEquals(GameConfig.numCols, 7);

    int[][] columnSets =
        new int[][] { new int[] { 0, 1 }, new int[] { 2, 3 },
            new int[] { 4, 5 } };
    // play first six columns
    for (int[] columns : columnSets) {
      for (int count = 0; count < 2; count++) {
        playMove(columns[0]);
        playMove(columns[1]);
      }
      for (int count = 0; count < 2; count++) {
        playMove(columns[1]);
        playMove(columns[0]);
      }
      for (int count = 0; count < 2; count++) {
        playMove(columns[0]);
        playMove(columns[1]);
      }
    }

    // play last column
    for (int row = 0; row < GameConfig.numRows; row++) {
      playMove(GameConfig.numCols - 1);
    }
  }

  @Test
  public void testNotifyMovePlayedAndNotifyGameFinished_whenGameWonOnLastMove() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public void testWhoHasNextChance_callRepeteadly() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());

    assertEquals(model.whoHasNextChance(), player1.getPlayerID());
    assertEquals(model.whoHasNextChance(), player1.getPlayerID());
  }

  @Test
  public void testNotifyGameFinished_whenGameNotStarted() {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is not in progress");
    model.notifyGameFinished(Player.player1);
  }

  // Failing currently as functionality not implemented yet.
  @Test
  public void testNotifyGameFinished_whenGameNotComplete() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is not over yet");
    model.notifyGameFinished(player1.getPlayerID());
  }

  @Test
  public void testGetGameState_whenGameNotStarted() {
    assertNotEquals(model.getGameState(), null);
  }

  @Test
  public void testIsGameOn_allTimesBeforeEnd() {
    assertEquals(model.isGameOn(), false);

    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);

    assertEquals(model.isGameOn(), false);

    model.startGame(player1.getName());
    assertEquals(model.isGameOn(), true);
  }

  @Test
  public void testStartGame_startWithOnePlayer() {
    model.addObserver(new TestPlayer(model, "test"));

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Cannot start game, all players not added");

    model.startGame("test");
  }

  @Test
  public void testStartGame_startWhenGameIsOn() {
    String name = addTwoPlayersToModel();
    model.startGame(name);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is in progress");

    model.startGame(name);
  }

  @Test
  public void testStartGame_withWrongPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player name not correct");
    model.startGame("wrong name");
  }

  @Test
  public void testStartGame_withNullPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player name not correct");
    model.startGame(null);
  }

  @Test
  public void testStartGame_withEmptyPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player name not correct");
    model.startGame("");
  }

  @Test
  public void testStartGame_TwoPlayersOneObserver_checkPlayerID() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");
    TestObserver obs = new TestObserver(model);

    model.addObserver(player1);
    model.addObserver(player2);
    model.addObserver(obs);

    model.startGame("1");

    assertTrue(player1.getPlayerID() == Player.player1
        || player1.getPlayerID() == Player.player2);
    assertTrue(player2.getPlayerID() == Player.player1
        || player2.getPlayerID() == Player.player2);
    assertTrue(obs.getPlayerID() == Player.none);
  }

  @Test
  public void testStartGame_TwoPlayersOneObserver_checkFirstChance() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");
    TestObserver obs = new TestObserver(model);

    model.addObserver(player1);
    model.addObserver(player2);
    model.addObserver(obs);

    model.startGame("1");

    assertEquals(model.whoHasNextChance(), player1.getPlayerID());
  }

  @Test
  public void testRemoveObserver_removePlayerWhenGameOn() {
    TestPlayer player1 = new TestPlayer(model, "1");
    TestPlayer player2 = new TestPlayer(model, "2");

    model.addObserver(player1);
    model.addObserver(player2);

    model.startGame(player1.getName());

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Cannot remove player when game is in progress");
    model.removeObserver(player1);
  }
}
