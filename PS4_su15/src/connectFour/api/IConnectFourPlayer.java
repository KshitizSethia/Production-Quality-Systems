package connectFour.api;

/**
 * Game "observer" in the observer pattern. Needs a reference to the
 * {@link IConnectFourModel} it's added to, so that it can play moves.
 * <p>
 * Implement this to make a player in the game who can make moves as well.
 * <p>
 * Implement {@link IConnectFourObserver} to make a passive game observer who
 * never plays a move.
 * 
 * @author Kshitiz
 *
 */
public interface IConnectFourPlayer extends IConnectFourObserver {

  /**
   * 
   * @return name of the player
   */
  public String getName();

}
