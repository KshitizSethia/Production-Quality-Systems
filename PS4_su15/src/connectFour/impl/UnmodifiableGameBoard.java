package connectFour.impl;

/**
 * An immutable game board which gives info about the state of the current game.
 * 
 * @author Kshitiz
 *
 */
public final class UnmodifiableGameBoard {

  private GameBoard board;

  UnmodifiableGameBoard(GameBoard board) {
    this.board = board;
  }

  /**
   * Get which {@link Player} has occupied which position on a
   * {@link GameConfig#numRows} x {@link GameConfig#numCols} game board.
   * 
   * @return 2d array of {@link Player} 
   */
  public Player[][] getBoardSate() {
    return board.getBoardState();
  }

  /**
   * Determine whether the supplied move will lead to a win
   * 
   * @param move
   *          to be evaluated
   * @return whether the move leads to a win
   */
  public boolean isWinningMove(Move move) {
    return board.isWinningMove(move);
  }

  /**
   * Get if the game board is full
   * 
   * @return
   */
  public boolean isFull() {
    return board.isFull();
  }

  /**
   * Get row where the chip would land if a chip is dropped into the specified
   * column
   * 
   * @param column
   * @return row number where the chip will drop (goes from 0 to max) (-1 if
   *         column is full)
   */
  public int getRow(int column) {
    return board.getRow(column);
  }

  @Override
  public String toString() {
    return board.toString();
  }
}
