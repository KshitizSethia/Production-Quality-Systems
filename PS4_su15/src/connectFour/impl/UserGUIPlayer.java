package connectFour.impl;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourPlayer;

final class UserGUIPlayer implements IConnectFourPlayer {

  // observer variables
  private IConnectFourModel  model;
  private String             name;
  private Player             playerID;

  // GUI variables
  private JFrame             mainFrame;
  private JButton[]          buttons;
  private JPanel[][]         boardOnDisplay;
  private static final Color myColor       = Color.GREEN;
  private static final Color opponentColor = Color.BLUE;
  private static final int   cellSize      = 100;

  UserGUIPlayer(IConnectFourModel model, String name) {
    // throw exceptions if args are wrong
    if (name == null || name == "") {
      throw new IllegalArgumentException("name");
    }
    if (model == null) {
      throw new IllegalArgumentException("model");
    }

    this.model = model;
    this.name = name;
    this.playerID = Player.none;

    createAndShowGUI();
  }

  private void createAndShowGUI() {
    // setup main view
    // TODO remove hard-coding of "green" below
    mainFrame = new JFrame("Connect 4, screen for " + name + " (in Green)");
    mainFrame.setResizable(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GridLayout gridLayout =
        new GridLayout(GameConfig.numRows + 1, GameConfig.numCols);
    mainFrame.setLayout(gridLayout);
    Container contentPane = mainFrame.getContentPane();

    // add buttons and panels
    ActionListener buttonListener = getButtonListener();
    addButtonsToTopRow(contentPane, buttonListener);
    addGameBoardPanels(contentPane);

    // set size of view
    mainFrame.setSize(cellSize * gridLayout.getColumns(),
        cellSize * gridLayout.getRows());

    // make view visible
    mainFrame.setVisible(true);
  }

  private ActionListener getButtonListener() {
    ActionListener buttonListener = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        JButton sourceButton = (JButton) event.getSource();
        int column = Integer.parseInt(sourceButton.getName());
        int row = model.getGameState().getRow(column);

        setButtonsEnabled(false);

        model.notifyMovePlayed(new Move(row, column, playerID));
      }
    };
    return buttonListener;
  }

  private void addButtonsToTopRow(Container contentPane,
      ActionListener buttonListener) {
    buttons = new JButton[GameConfig.numCols];
    for (int col = 0; col < buttons.length; col++) {
      JButton button = new JButton("Col " + String.valueOf(col));
      button.setBackground(myColor);
      button.setName(String.valueOf(col));
      button.addActionListener(buttonListener);

      buttons[col] = button;
      contentPane.add(button);
    }
    setButtonsEnabled(false);
  }

  private void setButtonsEnabled(boolean isEnabled) {
    for (JButton button : buttons) {
      button.setEnabled(isEnabled);
    }
  }

  private Color getColorForPlayer(Player player) {
    if (player == Player.none) {
      return Color.GRAY;
    }
    return player == playerID ? myColor : opponentColor;
  }

  private void addGameBoardPanels(Container contentPane) {
    boardOnDisplay = new JPanel[GameConfig.numRows][GameConfig.numCols];

    for (int row = 0; row < boardOnDisplay.length; row++) {
      for (int col = 0; col < boardOnDisplay[0].length; col++) {
        JPanel panel = new JPanel();

        boardOnDisplay[row][col] = panel;
        contentPane.add(panel);
      }
    }
    resetBoard();
  }

  private void resetBoard() {
    for (JPanel[] row : boardOnDisplay) {
      for (JPanel panel : row) {
        panel.setBackground(getColorForPlayer(Player.none));
      }
    }
  }

  @Override
  public void gameStarted(Player assignedPlayerID) {

    playerID = assignedPlayerID;

    if (model.whoHasNextChance() == playerID) {
      setButtonsEnabled(true);
    } else {
      setButtonsEnabled(false);
    }

  }

  @Override
  public void movePlayed(Move move) {
    // update new color on UI
    JPanel panelToUpdate = boardOnDisplay[move.getRow()][move.getCol()];
    Color newColor = getColorForPlayer(move.getPlayedBy());
    panelToUpdate.setBackground(newColor);

    // if this player's chance enable buttons
    if (model.whoHasNextChance() == playerID) {
      setButtonsEnabled(true);

      // disable buttons for columns which are full
      Player[] topRow = model.getGameState().getBoardSate()[0];
      for (int col = 0; col < GameConfig.numCols; col++) {
        if (topRow[col] != Player.none) {
          buttons[col].setEnabled(false);
        }
      }
    }
  }

  @Override
  public void gameFinished(Player winningPlayerID) {
    setButtonsEnabled(false);
    showResultToUserAndStartNewGame(winningPlayerID);

    resetBoard();
  }

  private void showResultToUserAndStartNewGame(Player winningPlayerID) {
    String title = "Game Over";
    String message;

    if (winningPlayerID == Player.none) {
      message = "Nobody";
    } else {
      message = (winningPlayerID == playerID ? "You" : "Opponent");
    }

    message += " won, Starting another game.";

    JOptionPane.showMessageDialog(mainFrame, message, title,
        JOptionPane.OK_OPTION);

    if (!model.isGameOn()) {
      model.notifyGameStarted(name);
    }
  }

  @Override
  public String getName() {
    return name;
  }
}
