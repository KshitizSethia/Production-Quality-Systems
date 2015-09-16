package connectFour.test;

import connectFour.api.IConnectFourModel;
import connectFour.api.IConnectFourPlayer;

public class TestPlayer extends TestObserver implements IConnectFourPlayer {

  public String name;

  public TestPlayer(IConnectFourModel model, String name) {
    super(model);
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

}
