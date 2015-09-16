package connectFour.impl;

/**
 * Move made by a {@link Player}
 * 
 * @author Kshitiz
 *
 */
public final class Move {

  private int    col;
  private int    row;
  private Player playedBy;

  /**
   * Immutable Move
   * 
   * @param row
   *          of the move
   * @param col
   *          column of the move
   * @param playedBy
   *          who plays this move
   */
  public Move(int row, int col, Player playedBy) {
    this.row = row;
    this.col = col;
    this.playedBy = playedBy;
  }

  /**
   * Get row of the move
   * 
   * @return
   */
  public int getRow() {
    return row;
  }

  /**
   * Get column of the move
   * 
   * @return
   */
  public int getCol() {
    return col;
  }

  /**
   * Get who plays the move
   * 
   * @return
   */
  public Player getPlayedBy() {
    return playedBy;
  }

}
