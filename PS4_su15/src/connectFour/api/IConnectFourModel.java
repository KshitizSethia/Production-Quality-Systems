package connectFour.api;

import connectFour.impl.GameConfig;
import connectFour.impl.Move;
import connectFour.impl.Player;
import connectFour.impl.UnmodifiableGameBoard;

/**
 * The core of the ConnectFour game, the "Model" in the observer pattern. Needs
 * to be thread-safe.
 * 
 * @author Kshitiz
 *
 */
public interface IConnectFourModel {

  /**
   * Add an observer to the game.
   * 
   * @param observer
   *          can be {@link IConnectFourObserver} or {@link IConnectFourPlayer}
   * @throws IllegalStateException
   *           if adding {@link IConnectFourPlayer} and the game is in progress
   * @throws IllegalStateException
   *           if {@link GameConfig#maxPlayers} number of
   *           {@link IConnectFourPlayer}s have been added to the game
   * @throws IllegalArgumentException
   *           if {@link IConnectFourPlayer#getName()} returns a name which is
   *           already in the game.
   */
  public void addObserver(IConnectFourObserver observer);

  /**
   * Remove an observer from the game.
   * <p/>
   * Notes to implementer:
   * <li>decrement {@link IConnectFourPlayer} count if removing
   * {@link IConnectFourPlayer}.</li>
   * <p/>
   * 
   * @param observer
   *          can be {@link IConnectFourObserver} or {@link IConnectFourPlayer}
   * @throws IllegalStateException
   *           if removing (@link IGamePlayer} when game is in progress
   * @throws IllegalArgumentException
   *           if unable to remove the {@link IConnectFourObserver}
   */
  public void removeObserver(IConnectFourObserver observer);

  /**
   * Notify to all {@link IConnectFourObserver}s that the game has started
   * <p/>
   * Notes to implementer:
   * <ul>
   * <li>clear the game state
   * <li>call {@link IConnectFourObserver#gameStarted(Player)} on all
   * {@link IConnectFourObserver}
   * <ul>
   * <li>{@link IConnectFourPlayer} gets {@link Player#player1} or
   * {@link Player#player2} assigned to it.
   * <li>{@link IConnectFourObserver} other than {@link IConnectFourPlayer} gets
   * {@link Player#none} assigned to it.
   * </ul>
   * </li>
   * </ul>
   * <p/>
   * 
   * @param firstPlayerName
   *          {@link IConnectFourPlayer#getName()} of player who gets to play
   *          the first chance in all games
   * @throws IllegalStateException
   *           if called when game has already been started
   * @throws IllegalStateException
   *           if called when all players are not in game
   */
  public void notifyGameStarted(String firstPlayerName);

  /**
   * Notify to all {@link IConnectFourObserver}s that a move has been played by
   * one of the players.
   * <p/>
   * Notes to imlementer:
   * <li>change state to reflect the move
   * <li>switch chance for next user
   * <li>notify all {@link IConnectFourObserver}s of the chance
   * <li>check if game has ended (win or draw) and notify accordingly
   * <p/>
   * 
   * @param move
   *          played by an {@link IConnectFourPlayer} who is part of the game.
   * @throws IllegalArgumentException
   *           if {@link Move} is played by someone other than the player.
   * @throws IllegalStateException
   *           if called when game is not in progress.
   * @throws IllegalArgumentException
   *           if move by player who doesn't have chance as stated by
   *           {@link #whoHasNextChance()}
   * 
   */
  public void notifyMovePlayed(Move move);

  /**
   * Notify to all {@link IConnectFourObserver}s that the game has ended.
   * <p/>
   * Notes to implementer:
   * <li>change state to reflect game has ended
   * <li>notify all {@link IConnectFourObserver}s
   * <p/>
   * 
   * @param playerID
   *          of the winning player. {@link Player#none} if result is a draw.
   * @throws IllegalStateException
   *           if called when game is not in progress
   */
  public void notifyGameFinished(Player playerID);

  /**
   * Get which {@link Player} is supposed to play the next chance
   * 
   * @return
   */
  public Player whoHasNextChance();

  /**
   * Get instance of {@link UnmodifiableGameBoard} with a link to the game's
   * state.
   * 
   * @return
   */
  UnmodifiableGameBoard getGameState();

  /**
   * Get whether the game is in progress.
   * 
   * @return
   */
  public boolean isGameOn();

  /**
   * Start a game with first chance assigned to the supplied player.
   * 
   * @param nameOfPlayerWithFirstChance
   *          {@link IConnectFourPlayer#getName()} of player who gets to play
   *          the first chance in every game.
   * @throws IllegalStateException
   *           if called when game has already been started
   * @throws IllegalStateException
   *           if called when all players are not in game
   */
  public void startGame(String nameOfPlayerWithFirstChance);
}
