import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Test;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

public class StopwatchFactoryTest {

  @Test
  public void testGetStopwatch() {
    String guid = UUID.randomUUID().toString();

    IStopwatch stopwatch1 = StopwatchFactory.getStopwatch(guid);
    assertTrue(StopwatchFactory.getStopwatches().size() == 1);
    assertTrue(StopwatchFactory.getStopwatches().get(0).equals(stopwatch1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStopwatch_addDuplicates() {
    String guid = UUID.randomUUID().toString();

    StopwatchFactory.getStopwatch(guid);
    
    StopwatchFactory.getStopwatch(guid);
    fail("getting stopwatch with same guid succeeded");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStopwatch_addNull() {
    StopwatchFactory.getStopwatch(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStopwatch_addEmpty() {
    StopwatchFactory.getStopwatch("");
  }
  
  @Test
  public void testGetStopwatches_empty(){
    assertTrue(StopwatchFactory.getStopwatches().size()==0);
  }
}
