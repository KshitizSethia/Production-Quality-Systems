package connectFour.impl;

import javax.swing.JOptionPane;

import connectFour.api.IConnectFourModel;

/**
 * Class with main() to run the game.
 * 
 * @author Kshitiz
 *
 */
public class ConnectFourRunner {

  public static void main(String[] args) {
    int response =
        JOptionPane.showOptionDialog(null,
            "Do you want to play single or double player game?",
            "Connect Four", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, new String[] { "Single",
                "Double" }, null);

    if (response == 1) {
      IConnectFourModel model =
          ConnectFourGameCreator.createTwoPlayerGame("Player 1", "Player 2");
      model.startGame("Player 1");
    } else if (response == 0) {
      IConnectFourModel model =
          ConnectFourGameCreator.createSinglePlayerGame("User");
      model.startGame("User");
    }
  }

}
