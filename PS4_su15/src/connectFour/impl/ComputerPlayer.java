package connectFour.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourPlayer;

final class ComputerPlayer implements IConnectFourPlayer {

  private IConnectFourModel model;
  private String            name;
  private Player            playerID;

  /**
   * Create a computer player
   * 
   * @param model
   *          to which this player will be added
   * @param name
   *          of the player
   */
  ComputerPlayer(IConnectFourModel model, String name) {
    this.model = model;
    this.name = name;
  }

  @Override
  public void gameStarted(Player assignedPlayerID) {
    playerID = assignedPlayerID;

    if (model.whoHasNextChance() == playerID) {
      playComputerMove(model.getGameState());
    }
  }

  @Override
  public void movePlayed(Move move) {
    UnmodifiableGameBoard board = model.getGameState();

    // computer plays a move if it has chance and the last move was not a
    // winning move
    if (model.whoHasNextChance() == playerID && !board.isWinningMove(move)) {
      playComputerMove(board);
    }
  }

  private void playComputerMove(UnmodifiableGameBoard board) {
    Move computerMove = getBestMove(board);
    model.notifyMovePlayed(computerMove);
  }

  private Move getBestMove(UnmodifiableGameBoard board) {
    Player[][] boardState = board.getBoardSate();

    List<Move> moves = getPossibleMoves(boardState);

    Move result = getRandomMove(moves);

    for (Move move : moves) {
      if (myWin(board, move) || opponentWin(board, move)) {
        result = new Move(move.getRow(), move.getCol(), playerID);
        break;
      }
    }
    return result;
  }

  private boolean myWin(UnmodifiableGameBoard board, Move move) {
    return board
        .isWinningMove(new Move(move.getRow(), move.getCol(), playerID));
  }

  private boolean opponentWin(UnmodifiableGameBoard board, Move move) {
    Player opponentID =
        playerID == Player.player1 ? Player.player2 : Player.player1;

    Move opponentMove = new Move(move.getRow(), move.getCol(), opponentID);

    return board.isWinningMove(opponentMove);
  }

  private Move getRandomMove(List<Move> possibleMoves) {
    Move randomMove =
        possibleMoves.get(new Random().nextInt(possibleMoves.size()));
    Move computerMove =
        new Move(randomMove.getRow(), randomMove.getCol(), playerID);
    return computerMove;
  }

  private List<Move> getPossibleMoves(Player[][] boardState) {
    List<Move> results = new ArrayList<Move>();
    for (int col = 0; col < GameConfig.numCols; col++) {

      for (int row = GameConfig.numRows - 1; row >= 0; row--) {
        if (boardState[row][col] == Player.none) {
          results.add(new Move(row, col, boardState[row][col]));
          break;
        }
      }
    }
    return results;
  }

  @Override
  public void gameFinished(Player winningPlayerID) {
    // do nothing
  }

  @Override
  public String getName() {
    return name;
  }

}
