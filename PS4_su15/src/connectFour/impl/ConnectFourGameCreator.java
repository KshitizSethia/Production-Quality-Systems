package connectFour.impl;

import java.util.UUID;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourObserver;
import connectFour.api.IConnectFourPlayer;

/**
 * Create a Connect Four game instance.
 * 
 * @author Kshitiz
 *
 */
public class ConnectFourGameCreator {

  private ConnectFourGameCreator() {
    // prevent instantiation
  }

  /**
   * Create a single player game.
   * <p>
   * Call {@link IConnectFourModel#startGame(String)} to start game.
   * <p>
   * More {@link IConnectFourObserver}s can still be added before starting the
   * game.
   * 
   * @param playerName
   *          of the user
   * @return instance of {@link IConnectFourModel} with one user and a computer
   *         player added
   */
  public static IConnectFourModel createSinglePlayerGame(String playerName) {
    IConnectFourModel model = new ConnectFourModel();

    model.addObserver(new UserGUIPlayer(model, playerName));
    model.addObserver(new ComputerPlayer(model, UUID.randomUUID().toString()));
    return model;
  }

  /**
   * Create a two player game.
   * <p>
   * Call {@link IConnectFourModel#startGame(String)} to start game.
   * <p>
   * More {@link IConnectFourObserver}s can still be added before starting the
   * game.
   * 
   * @param firstPlayerName
   * @param secondPlayerName
   * @return instance of {@link IConnectFourModel} with both players added
   */
  public static IConnectFourModel createTwoPlayerGame(String firstPlayerName,
      String secondPlayerName) {
    IConnectFourModel model = new ConnectFourModel();

    model.addObserver(new UserGUIPlayer(model, firstPlayerName));
    model.addObserver(new UserGUIPlayer(model, secondPlayerName));
    return model;
  }

  /**
   * Create a game with no {@link IConnectFourPlayer} and
   * {@link IConnectFourObserver} added.
   * 
   * @return instance of {@link IConnectFourModel}
   */
  public static IConnectFourModel createModelWithoutPlayers() {
    return new ConnectFourModel();
  }
}
