package connectFour.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import connectFour.api.IConnectFourModel;
import connectFour.test.TestPlayer;

public class UnmodifiableGameBoardTest {

  IConnectFourModel model;
  TestPlayer        player1;
  TestPlayer        player2;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // these tests are designed for winning length of four
    assertEquals(GameConfig.winningLength, 4);
  }

  @Before
  public void startGameWithFirstChanceToPlayer1() throws Exception {
    model = ConnectFourGameCreator.createModelWithoutPlayers();
    player1 = new TestPlayer(model, "1");
    player2 = new TestPlayer(model, "2");
    model.addObserver(player1);
    model.addObserver(player2);
    model.startGame(player1.getName());
  }

  private void player1Move(int column) {
    Move move = getMove(column, player1.getPlayerID());
    model.notifyMovePlayed(move);
  }

  private void player2Move(int column) {
    Move move = getMove(column, player2.getPlayerID());
    model.notifyMovePlayed(move);
  }

  private Move getMove(int column, Player playerID) {
    int row = model.getGameState().getRow(column);
    Move move = new Move(row, column, playerID);
    return move;
  }

  @Test
  public void testBoardState_twoPlayers_twoMoves() {
    player1Move(0);
    player2Move(1);
    Player[][] boardState = model.getGameState().getBoardSate();

    Player[] emptyRow = getEmptyBoardRow();
    for (int row = 0; row < GameConfig.numRows - 2; row++) {
      assertArrayEquals(boardState[row], emptyRow);
    }
    Player[] playedRow = emptyRow;
    playedRow[0] = player1.getPlayerID();
    playedRow[1] = player2.getPlayerID();
    assertArrayEquals(boardState[GameConfig.numRows - 1], playedRow);
  }

  private Player[] getEmptyBoardRow() {
    Player[] emptyRow = new Player[GameConfig.numCols];
    for (int col = 0; col < emptyRow.length; col++) {
      emptyRow[col] = Player.none;
    }
    return emptyRow;
  }

  @Test
  public void testIsWinningMove_horizontal_onFirstColumn() {
    setUpBoardForWinningMove(new int[] { 3, 2, 1 }, 6);

    Move winningMove = getMove(0, player1.getPlayerID());
    assertTrue(model.getGameState().isWinningMove(winningMove));
  }

  private void setUpBoardForWinningMove(int[] player1Moves,
      int player2RepeatedMove) {
    for (int column : player1Moves) {

      Move move1 = getMove(column, player1.getPlayerID());
      assertFalse(model.getGameState().isWinningMove(move1));
      model.notifyMovePlayed(move1);

      Move move2 = getMove(player2RepeatedMove, player2.getPlayerID());
      assertFalse(model.getGameState().isWinningMove(move2));
      model.notifyMovePlayed(move2);
    }
  }

  private void setUpBoardForWinningMove(int[] movePairs) {
    for (int index = 0; index < movePairs.length; index += 2) {

      Move move1 = getMove(movePairs[index], player1.getPlayerID());
      assertFalse(model.getGameState().isWinningMove(move1));
      model.notifyMovePlayed(move1);

      Move move2 = getMove(movePairs[index + 1], player2.getPlayerID());
      assertFalse(model.getGameState().isWinningMove(move2));
      model.notifyMovePlayed(move2);
    }
  }

  @Test
  public void testIsWinningMove_horizontal_onLastColumn() {
    setUpBoardForWinningMove(new int[] { 3, 4, 5 }, 0);

    Move winningMove = getMove(6, player1.getPlayerID());
    assertTrue(model.getGameState().isWinningMove(winningMove));
  }

  @Test
  public void testIsWinningMove_horizontal_secondInSequence() {
    setUpBoardForWinningMove(new int[] { 1, 3, 4 }, 0);

    Move winningMove = getMove(2, player1.getPlayerID());
    assertTrue(model.getGameState().isWinningMove(winningMove));
  }

  @Test
  public void testIsWinningMove_horizontal_thirdInSequence() {
    setUpBoardForWinningMove(new int[] { 1, 2, 4 }, 0);

    Move winningMove = getMove(3, player1.getPlayerID());
    assertTrue(model.getGameState().isWinningMove(winningMove));
  }

  @Test
  public void testIsWinningMove_vertical_topmostRow() {
    setUpBoardForWinningMove(new int[] { 0, 0, 0 }, 1);

    Move winningMove = getMove(0, player1.getPlayerID());
    assertTrue(model.getGameState().isWinningMove(winningMove));
  }

  @Test
  public void testIsWinningMove_upDiagonal_lastInSequenceOnTopRightCorner() {
    int[] setupSequence =
        new int[] { 6, 5, 6, 5, 5, 6, 5, 6, 5, 6, 4, 3, 4, 3, 3, 4, 4, 2 };
    testSequenceAndWinningMove(setupSequence, 6);
  }

  private void testSequenceAndWinningMove(int[] setupSequence,
      int winningMoveColumn) {
    setUpBoardForWinningMove(setupSequence);

    Move winningMove = getMove(winningMoveColumn, player1.getPlayerID());
    assertTrue(model.getGameState().isWinningMove(winningMove));
  }

  @Test
  public void
      testIsWinningMove_upDiagonal_lastInSequenceOnRightmostColButNotInTopRow() {
    int[] setupSequence = new int[] { 3, 4, 4, 5, 5, 6, 5, 6, 2, 6 };
    testSequenceAndWinningMove(setupSequence, 6);
  }

  @Test
  public void testIsWinningMove_upDiagonal_secondInSequence() {
    int[] setupSequence = new int[] { 3, 4, 5, 6, 5, 6, 5, 6, 6, 2 };
    testSequenceAndWinningMove(setupSequence, 4);
  }

  @Test
  public void testIsWinningMove_upDiagonal_thirdInSequence() {
    int[] setupSequence = new int[] { 3, 4, 4, 5, 5, 6, 6, 6, 6, 2 };
    testSequenceAndWinningMove(setupSequence, 5);
  }

  @Test
  public void testIsWinningMove_upDiagonal_firstInSequenceOnBottomLeftCorner() {
    int[] setupSequence = new int[] { 1, 2, 1, 2, 2, 3, 3, 3, 3, 4 };
    testSequenceAndWinningMove(setupSequence, 0);
  }

  @Test
  public void
      testIsWinningMove_upDiagonal_firstInSequenceOnFirstColumnButNotLastRow() {
    int[] setupSequence =
        new int[] { 1, 2, 1, 2, 1, 0, 2, 3, 2, 3, 3, 3, 3, 4 };
    testSequenceAndWinningMove(setupSequence, 0);
  }

  @Test
  public void
      testIsWinningMove_downDiagonal_lastInSequenceOnBottomRightCorner() {
    int[] setupSequence = new int[] { 3, 4, 3, 4, 4, 5, 5, 3, 3, 2 };
    testSequenceAndWinningMove(setupSequence, 6);
  }

  @Test
  public
      void
      testIsWinningMove_downDiagonal_lastInSequenceOnRightmostColButNotInLastRow() {
    int[] setupSequence =
        new int[] { 5, 6, 4, 5, 5, 4, 4, 3, 4, 3, 3, 3, 3, 2 };
    testSequenceAndWinningMove(setupSequence, 6);
  }

  @Test
  public void testIsWinningMove_downDiagonal_secondInSequence() {
    int[] setupSequence = new int[] { 1, 2, 1, 2, 4, 3, 3, 1, 1, 0 };
    testSequenceAndWinningMove(setupSequence, 2);
  }

  @Test
  public void testIsWinningMove_downDiagonal_thirdInSequence() {
    int[] setupSequence = new int[] { 1, 2, 1, 2, 2, 1, 1, 3, 4, 5 };
    testSequenceAndWinningMove(setupSequence, 3);
  }

  @Test
  public void testIsWinningMove_downDiagonal_firstInSequenceOnTopLeftCorner() {
    int[] setupSequence =
        new int[] { 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 2, 3, 2, 3, 3, 2, 2, 4 };
    testSequenceAndWinningMove(setupSequence, 0);
  }

  @Test
  public void
      testIsWinningMove_downDiagonal_firstInSequenceOnFirstColumnButNotTopRow() {
    int[] setupSequence = new int[] { 3, 2, 2, 1, 0, 1, 1, 0, 0, 4 };
    testSequenceAndWinningMove(setupSequence, 0);
  }

  @Test
  public void testToString() {
    player1Move(0);
    player2Move(1);
    String actual = model.getGameState().toString();
    String expected =
        "    0   1   2   3   4   5   6  \n"
            + " 0  ..  ..  ..  ..  ..  ..  .. \n"
            + " 1  ..  ..  ..  ..  ..  ..  .. \n"
            + " 2  ..  ..  ..  ..  ..  ..  .. \n"
            + " 3  ..  ..  ..  ..  ..  ..  .. \n"
            + " 4  ..  ..  ..  ..  ..  ..  .. \n"
            + " 5  p1  p2  ..  ..  ..  ..  .. \n";
    assertEquals(actual, expected);
  }
}
