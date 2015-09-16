package connectFour.impl;

final class GameBoard {

  private Player[][] boardState;
  private Object     lock;

  GameBoard() {
    lock = new Object();
    resetBoard();
  }

  void addMove(Move move) {
    synchronized (lock) {
      if (!isMoveValid(move)) {
        throw new IllegalArgumentException("Invalid move");
      }
      boardState[move.getRow()][move.getCol()] = move.getPlayedBy();
    }
  }

  private boolean isMoveValid(Move move) {
    return isMoveWithinLimits(move) && isSpotVacant(move)
        && areAllBelowOccupied(move);
  }

  private boolean isSpotVacant(Move move) {
    return boardState[move.getRow()][move.getCol()] == Player.none;
  }

  private boolean areAllBelowOccupied(Move move) {
    boolean allBelowOccupied = true;
    for (int row = move.getRow() + 1; row < GameConfig.numRows; row++) {
      if (boardState[row][move.getCol()] == Player.none) {
        allBelowOccupied = false;
      }
    }
    return allBelowOccupied;
  }

  boolean isWinningMove(Move move) {
    synchronized (lock) {
      return isHorizontalWin(move) || isVerticalWin(move)
          || isUpDiagonalWin(move) || isDownDiagonalWin(move);
    }
  }

  private boolean isVerticalWin(Move move) {
    int downIndex = move.getRow();
    for (int row = move.getRow() + 1; row < GameConfig.numRows; row++) {
      if (boardState[row][move.getCol()] != move.getPlayedBy()) {
        break;
      } else {
        downIndex = row;
      }
    }

    return (downIndex - move.getRow() + 1) >= GameConfig.winningLength;
  }

  private boolean isHorizontalWin(Move move) {
    Player player = move.getPlayedBy();
    Player[] row = boardState[move.getRow()];

    int leftIndex = move.getCol();
    for (int col = move.getCol() - 1; col >= 0; col--) {
      if (row[col] == player) {
        leftIndex = col;
      } else {
        break;
      }
    }

    int rightIndex = move.getCol();
    for (int col = move.getCol() + 1; col < GameConfig.numCols; col++) {
      if (row[col] == player) {
        rightIndex = col;
      } else {
        break;
      }
    }

    return (rightIndex - leftIndex + 1) >= GameConfig.winningLength;
  }

  private boolean isUpDiagonalWin(Move move) {
    Player player = move.getPlayedBy();

    int leftCol = move.getCol();
    for (int row = move.getRow() - 1; row >= 0 && leftCol > 0; row--) {
      int col = leftCol - 1;
      if (boardState[row][col] == player) {
        leftCol--;
      } else {
        break;
      }
    }

    int rightCol = move.getCol();
    for (int row = move.getRow() + 1; row < GameConfig.numRows
        && rightCol < GameConfig.numCols - 1; row++) {
      int col = rightCol + 1;
      if (boardState[row][col] == player) {
        rightCol++;
      } else {
        break;
      }
    }

    return (rightCol - leftCol + 1) >= GameConfig.winningLength;
  }

  private boolean isDownDiagonalWin(Move move) {
    Player player = move.getPlayedBy();

    int leftCol = move.getCol();
    for (int row = move.getRow() + 1; row < GameConfig.numRows && leftCol > 0; row++) {
      int col = leftCol - 1;
      if (boardState[row][col] == player) {
        leftCol--;
      } else {
        break;
      }
    }

    int rightCol = move.getCol();
    for (int row = move.getRow() - 1; row >= 0
        && rightCol < GameConfig.numCols - 1; row--) {
      int col = rightCol + 1;
      if (boardState[row][col] == player) {
        rightCol++;
      } else {
        break;
      }
    }

    return (rightCol - leftCol + 1) >= GameConfig.winningLength;
  }

  boolean isFull() {
    synchronized (lock) {
      for (Player[] row : boardState) {
        for (Player entry : row) {
          if (entry == Player.none) {
            return false;
          }
        }
      }
      return true;
    }
  }

  void resetBoard() {
    synchronized (lock) {
      boardState = new Player[GameConfig.numRows][GameConfig.numCols];
      for (int row = 0; row < GameConfig.numRows; row++) {
        for (int col = 0; col < GameConfig.numCols; col++) {
          boardState[row][col] = Player.none;
        }
      }
    }
  }

  Player[][] getBoardState() {
    synchronized (lock) {
      Player[][] result = new Player[GameConfig.numRows][];
      for (int row = 0; row < GameConfig.numRows; row++) {
        result[row] = boardState[row].clone();
      }
      return result;
    }
  }

  int getRow(int column) {
    synchronized (lock) {
      int result = -1;
      for (int row = GameConfig.numRows - 1; row >= 0; row--) {
        if (boardState[row][column] == Player.none) {
          result = row;
          break;
        }
      }
      return result;
    }
  }

  @Override
  public String toString() {
    StringBuilder sbr = new StringBuilder();

    sbr.append("   ");
    for (int index = 0; index < GameConfig.numCols; index++) {
      sbr.append(" " + index + "  ");
    }
    sbr.append("\n");

    for (int row = 0; row < GameConfig.numRows; row++) {
      sbr.append(" " + row + " ");
      for (int col = 0; col < GameConfig.numCols; col++) {
        switch (boardState[row][col]) {
          case player1:
            sbr.append(" p1 ");
            break;
          case player2:
            sbr.append(" p2 ");
            break;
          case none:
            sbr.append(" .. ");
            break;
        }
      }
      sbr.append("\n");
    }
    String result = sbr.toString();
    return result;
  }

  private boolean isMoveWithinLimits(Move move) {
    return move.getRow() >= 0 && move.getRow() < GameConfig.numRows
        && move.getCol() >= 0 && move.getCol() < GameConfig.numCols;
  }
}
