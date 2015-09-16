package connectFour.impl;


/**
 * Configuration of the game.
 * @author Kshitiz
 *
 */
public final class GameConfig {
  private GameConfig(){
    //prevent instantiation
  }
  /**
   * Max number of players allowed
   */
  public static final int maxPlayers = 2;
  /**
   * number of rows in the game board
   */
  public static final int numRows = 6;
  /**
   * Number of columns in the game board
   */
  public static final int numCols = 7;
  /**
   * Minimum consecutive length required to win
   * A win can be horizontal, vertical or diagonal.
   */
  public static final int winningLength = 4;
}
