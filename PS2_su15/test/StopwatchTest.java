import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

public class StopwatchTest {

  private static final long delay    = 5000;
  private static final long minDelay = 4900;
  private static final long maxDelay = 5100;

  private long getMeanLapTime(List<Long> lapTimes, boolean keepLast) {
    // add lap times
    long avgLapTime = 0;
    for (long lapTime : lapTimes) {
      avgLapTime += lapTime;
    }
    // divide by size
    if (keepLast) {
      avgLapTime = Math.round(avgLapTime / lapTimes.size());
    } else {
      avgLapTime -= lapTimes.get(lapTimes.size() - 1);
      avgLapTime = Math.round(avgLapTime / lapTimes.size() - 1);
    }

    return avgLapTime;
  }

  @Test
  public void testStart_thenStop() {
    String guid = UUID.randomUUID().toString();

    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    assertTrue(stopwatch.getLapTimes().size() == 0);

    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 1);
    
    long avgLapTime = getMeanLapTime(stopwatch.getLapTimes(), true);
    assertTrue(avgLapTime == 0);
  }

  @Test
  public void testStart_Sleep_Stop() {
    String guid = UUID.randomUUID().toString();

    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    assertTrue(stopwatch.getLapTimes().size() == 0);

    try {
      Thread.sleep(delay);
    } catch (InterruptedException ignored) {
    }

    stopwatch.stop();
    
    assertTrue(stopwatch.getLapTimes().size() == 1);
    assertLapTimesWithinLimits(stopwatch.getLapTimes(), true);
  }

  @Test
  public void testStart_thenStop_repeatTwoTimes() {
    String guid = UUID.randomUUID().toString();

    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    assertTrue(stopwatch.getLapTimes().size() == 0);

    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 1);

    stopwatch.start();
    assertTrue(stopwatch.getLapTimes().size() == 1);

    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 2);

    long avgLapTime = getMeanLapTime(stopwatch.getLapTimes(), true);
    assertTrue(avgLapTime == 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testStart_alreadyStartedStopwatch() {
    String guid = UUID.randomUUID().toString();

    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    assertTrue(stopwatch.getLapTimes().size() == 0);

    stopwatch.start();
    fail("started a stopwatch which was already started");
  }

  @Test
  public void testHashCode() {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    assertFalse(guid.hashCode() == stopwatch.hashCode());
  }

  @Test
  public void testGetId() {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    assertTrue(guid.equals(stopwatch.getId()));
  }

  @Test
  public void testLap() {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 1);
    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 2);

    assertEquals(0, getMeanLapTime(stopwatch.getLapTimes(), true));
  }

  @Test
  public void testLap_twoLaps() {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 1);
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 2);
    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 3);

    assertEquals(0, getMeanLapTime(stopwatch.getLapTimes(), true));
  }

  @Test
  public void testLap_twoLapsWithDelay() throws InterruptedException {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    
    Thread.sleep(delay);
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 1);
    
    Thread.sleep(delay);
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 2);
    
    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 3);

    assertLapTimesWithinLimits(stopwatch.getLapTimes(), false);
  }
  
  @Test
  public void testLap_twoLapsWithMoreDelay() throws InterruptedException {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.start();
    
    Thread.sleep(delay);
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 1);
    
    Thread.sleep(delay);
    stopwatch.lap();
    assertTrue(stopwatch.getLapTimes().size() == 2);
    
    Thread.sleep(delay);
    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 3);

    assertLapTimesWithinLimits(stopwatch.getLapTimes(), true);
  }

  private void assertLapTimesWithinLimits(List<Long> lapTimes, boolean keepLast) {
    List<Long> sublist = lapTimes;
    if(!keepLast){
      sublist = lapTimes.subList(0, lapTimes.size()-2);
    }
    for(long lapTime : sublist){
      assertTrue(lapTime>minDelay && lapTime<maxDelay);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testStop_unstartedStopwatch() {
    String guid = UUID.randomUUID().toString();

    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);
    stopwatch.stop();
    fail("stopped a Stopwatch which was not started");
  }

  @Test
  public void testReset() {
    IStopwatch stopwatch =
        StopwatchFactory.getStopwatch(UUID.randomUUID().toString());

    stopwatch.start();
    stopwatch.lap();
    stopwatch.stop();

    stopwatch.reset();
    assertTrue(stopwatch.getLapTimes().size() == 0);
    stopwatch.start();
    assertTrue(stopwatch.getLapTimes().size() == 0);
    stopwatch.stop();
    assertTrue(stopwatch.getLapTimes().size() == 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testReset_stopAResetWatch() {
    IStopwatch stopwatch =
        StopwatchFactory.getStopwatch(UUID.randomUUID().toString());

    stopwatch.start();
    stopwatch.lap();
    stopwatch.stop();

    stopwatch.reset();
    stopwatch.stop();
  }

  @Test
  public void testToString() {
    String guid = UUID.randomUUID().toString();
    IStopwatch stopwatch = StopwatchFactory.getStopwatch(guid);

    String dump = stopwatch.toString();
    assertEquals(dump, "ID: " + guid + "; LapTimes: []");
  }

  // @Test
  // public void testClone() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testToString1() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testNotify() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testNotifyAll() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testWaitLong() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testWaitLongInt()
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testWait() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testFinalize() {
  // fail("Not yet implemented");
  // }

}
