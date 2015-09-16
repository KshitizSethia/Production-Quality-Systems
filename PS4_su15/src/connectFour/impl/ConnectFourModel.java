package connectFour.impl;

import java.util.LinkedList;
import java.util.List;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourObserver;
import connectFour.api.IConnectFourPlayer;

final class ConnectFourModel implements IConnectFourModel {

  // objects for game and it's progress
  private boolean                    gameOn;
  private GameBoard                  board;
  private Player                     nextChance;

  // lists of observers
  private List<IConnectFourObserver> observers;
  private int                        numPlayers;

  // lock
  private Object                     lock;

  ConnectFourModel() {
    gameOn = false;
    board = new GameBoard();
    nextChance = Player.none;

    observers = new LinkedList<IConnectFourObserver>();
    numPlayers = 0;

    lock = new Object();
  }

  @Override
  public void addObserver(IConnectFourObserver observer) {
    synchronized (lock) {
      // don't add player if game is on
      if (gameOn && observer instanceof IConnectFourPlayer) {
        throw new IllegalStateException(
            "Cannot add player when game is in progress.");
      }

      // add a player
      if (observer instanceof IConnectFourPlayer) {
        addPlayer(observer);
      }

      // add the observer
      observers.add(observer);

    }
  }

  private void addPlayer(IConnectFourObserver observer) {
    IConnectFourPlayer newPlayer = (IConnectFourPlayer) observer;

    // don't add players if max have already been added
    if (numPlayers == GameConfig.maxPlayers) {
      throw new IllegalStateException("Max number of players added");
    }

    // check if player name is unique
    for (IConnectFourObserver storedObserver : observers) {
      if (storedObserver instanceof IConnectFourPlayer) {
        IConnectFourPlayer storedPlayer = (IConnectFourPlayer) storedObserver;
        if (storedPlayer.getName() == newPlayer.getName()) {
          throw new IllegalArgumentException("Player name already taken");
        }
      }
    }

    // increase player count
    numPlayers++;
  }

  @Override
  public void removeObserver(IConnectFourObserver observer) {
    synchronized (lock) {
      // don't remove player if game is in progress
      if (gameOn && observer instanceof IConnectFourPlayer) {
        throw new IllegalStateException(
            "Cannot remove player when game is in progress");
      }

      // remove observer
      if (!observers.remove(observer)) {
        throw new IllegalArgumentException("Unable to remove");
      }

      // decrease player count if player
      if (observer instanceof IConnectFourPlayer) {
        numPlayers--;
      }
    }
  }

  @Override
  public void notifyGameStarted(String firstPlayerName) {
    synchronized (lock) {
      // throw exceptions
      if (firstPlayerName == null || notAPlayer(firstPlayerName)) {
        throw new IllegalArgumentException("player name not correct");
      }

      if (gameOn) {
        throw new IllegalStateException("Game is in progress");
      }

      if (numPlayers != GameConfig.maxPlayers) {
        throw new IllegalStateException(
            "Cannot start game, all players not added");
      }

      // good to go, change state
      gameOn = true;
      board.resetBoard();

      // playerID in order of iteration
      int playerIndex = 0;
      Player[] players = new Player[] { Player.player1, Player.player2 };

      // notify everyone, assign playerID and first chance
      for (IConnectFourObserver obs : observers) {
        if (obs instanceof IConnectFourPlayer) {
          IConnectFourPlayer player = (IConnectFourPlayer) obs;

          // assign first chance
          if (player.getName().equals(firstPlayerName)) {
            nextChance = players[playerIndex];
          }

          player.gameStarted(players[playerIndex]);

          playerIndex++;
        } else {
          obs.gameStarted(Player.none);
        }

      }
    }

  }

  private boolean notAPlayer(String firstPlayerName) {
    for (IConnectFourObserver obs : observers) {
      if (obs instanceof IConnectFourPlayer
          && ((IConnectFourPlayer) obs).getName().equals(firstPlayerName)) {
        return false;
      }
    }
    return true;
  }

  // TODO add authentication whether the move is played by a player who is part
  // of the game.
  @Override
  public void notifyMovePlayed(Move move) {
    // check that call is from a player only
    if (move.getPlayedBy() == null) {
      throw new IllegalArgumentException("Move.getPlayedBy() is null");
    }
    if (move.getPlayedBy() == Player.none) {
      throw new IllegalArgumentException("Move can only be played by a player");
    }

    synchronized (lock) {

      if (!gameOn) {
        throw new IllegalStateException("Game is not in progress");
      }

      if (whoHasNextChance() != move.getPlayedBy()) {
        throw new IllegalArgumentException("Moved without chance");
      }

      // play the move
      board.addMove(move);

      // give chance to other player
      switchChance();

      // notify everyone
      for (IConnectFourObserver obs : observers) {
        obs.movePlayed(move);
      }

      // see if game has ended
      if (board.isWinningMove(move)) {
        notifyGameFinished(move.getPlayedBy());
      } else if (board.isFull()) {
        notifyGameFinished(Player.none);
      }
    }
  }

  private void switchChance() {
    switch (nextChance) {
      case player1:
        nextChance = Player.player2;
        break;
      case player2:
        nextChance = Player.player1;
        break;
      default:
        throw new IllegalStateException(
            "trying to switch chance from invalid value");
    }

  }

  @Override
  public Player whoHasNextChance() {
    synchronized (lock) {
      return nextChance;
    }
  }

  @Override
  public void notifyGameFinished(Player playerID) {
    synchronized (lock) {
      if (!gameOn) {
        throw new IllegalStateException("Game is not in progress");
      }

      gameOn = false;

      for (IConnectFourObserver obs : observers) {
        obs.gameFinished(playerID);
      }
    }
  }

  @Override
  public UnmodifiableGameBoard getGameState() {
    return new UnmodifiableGameBoard(board);
  }

  @Override
  public boolean isGameOn() {
    synchronized (lock) {
      return gameOn;
    }
  }

  @Override
  public void startGame(String firstPlayerName) {
    notifyGameStarted(firstPlayerName);
  }
}
