package connectFour.test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourObserver;
import connectFour.impl.Move;
import connectFour.impl.Player;

public class TestObserver implements IConnectFourObserver {

  public IConnectFourModel model;
  protected Player         playerID;
  protected Player         winner;
  protected Move           lastMove;
  protected List<Notif>    notifs;
  protected Object         notifLock;

  public TestObserver(IConnectFourModel model) {
    this.model = model;
    notifs = new LinkedList<Notif>();
    notifLock = new Object();

  }

  @Override
  public void gameStarted(Player assignedPlayerID) {
    synchronized (notifLock) {
      playerID = assignedPlayerID;
      notifs.add(Notif.started);
    }
  }

  @Override
  public void movePlayed(Move move) {
    synchronized (notifLock) {
      lastMove = move;
      notifs.add(Notif.movePlayed);
    }
  }

  @Override
  public void gameFinished(Player winningPlayerID) {
    synchronized (notifLock) {
      winner = winningPlayerID;
      notifs.add(Notif.finished);
    }
  }

  public List<Notif> getNotifs() {
    synchronized (notifLock) {
      return new CopyOnWriteArrayList<Notif>(notifs);
    }
  }

  /**
   * @param index
   *          in python style (-1, -2, etc)
   */
  public Notif getNotifFromEnd(int index) {
    synchronized (notifLock) {
      int actualIndex = notifs.size() + index;
      return notifs.get(actualIndex);
    }
  }

  public Player getWinner() {
    synchronized (notifLock) {
      return winner;
    }
  }

  public Player getPlayerID() {
    synchronized (notifLock) {
      return playerID;
    }
  }

  public Move getLastMove() {
    synchronized (notifLock) {
      return lastMove;
    }
  }
}
