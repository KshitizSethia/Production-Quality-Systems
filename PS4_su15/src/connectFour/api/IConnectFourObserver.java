package connectFour.api;

import connectFour.impl.Move;
import connectFour.impl.Player;

/**
 * Game "observer" in the observer pattern.
 * <p>
 * Should store an instance of {@link IConnectFourModel}.
 * <p>
 * Implement this to make a passive game observer who never plays a move.
 * <p>
 * Implement {@link IConnectFourPlayer} to make a player in the game who can
 * make moves as well.
 * 
 * @author Kshitiz
 *
 */
public interface IConnectFourObserver {

  /**
   * Call to notify of start of game.
   * <p>
   * Store the playerId given here, use it while playing {@Link Move} on
   * the instance of {@link IConnectFourModel}.
   * <p>
   * Possible uses:
   * <li>Can be used to reset the UI.
   * 
   * @param assignedPlayerID
   *          assigned for the duration of the game.
   */
  public void gameStarted(Player assignedPlayerID);

  /**
   * Call to notify that a move has been made by a player. Notified of own moves
   * as well.
   * <p>
   * Possible uses:
   * <li>update own state about the game's progress
   * <li>used by an automated player to play it's turn
   * 
   * @param move
   *          made by {@link IConnectFourPlayer}
   */
  public void movePlayed(Move move);

  /**
   * Call to notify that the game has ended.
   * <p>
   * Possible uses:
   * <li>Show message to user on result of game
   * <li>reset the UI
   * <li>start another round of the same game
   * 
   * @param winningPlayerID
   *          {@link Player} who has won the game. {@link Player#none} if nobody
   *          has won the game.
   */
  public void gameFinished(Player winningPlayerID);

}
