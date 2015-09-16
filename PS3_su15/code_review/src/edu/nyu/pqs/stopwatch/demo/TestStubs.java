package edu.nyu.pqs.stopwatch.demo;

import java.util.List;
import java.util.logging.Logger;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

public class TestStubs {
  private static final Logger logger = Logger
      .getLogger("edu.nyu.pqs.ps4.demo.TestStubs");

  public static void recordTimeSimple(IStopwatch stopwatch) {
    stopwatch.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ie) { /* safely ignore this */
    }
    stopwatch.stop();
    logger.info(stopwatch.getLapTimes().toString());
  }

  public static void recordTimeWithLaps(IStopwatch stopwatch) {
    stopwatch.start();
    try {
      Thread.sleep(100);
      stopwatch.stop();
      Thread.sleep(200);
      stopwatch.start();
      Thread.sleep(300);
      stopwatch.lap();
      Thread.sleep(100);
    } catch (InterruptedException ie) { /* safely ignore this */
    }
    stopwatch.stop();
    logger.info(stopwatch.getLapTimes().toString());
  }

  public static void testWeirdOpsOnStopwatch() {
    IStopwatch stopwatch = StopwatchFactory.getStopwatch("weird");

    // can't reset a new watch
    try {
      stopwatch.reset();
    } catch (IllegalStateException ex) {
      logger.info(ex.toString());
    }
    // can't stop a new watch
    try {
      stopwatch.stop();
    } catch (IllegalStateException ex) {
      logger.info(ex.toString());
    }
    // can't start a running watch
    stopwatch.start();
    try {
      stopwatch.start();
    } catch (IllegalStateException ex) {
      logger.info(ex.toString());
    }
    // can't reset a started watch
    try {
      stopwatch.reset();
    } catch (IllegalStateException ex) {
      logger.info(ex.toString());
    }
    // can't stop a stopped watch
    stopwatch.stop();
    try {
      stopwatch.stop();
    } catch (IllegalStateException ex) {
      logger.info(ex.toString());
    }
    // can't reset a reset watch
    stopwatch.reset();
    try {
      stopwatch.reset();
    } catch (IllegalStateException ex) {
      logger.info(ex.toString());
    }
  }

  public static void main(String[] args) {
    IStopwatch stopwatch = StopwatchFactory.getStopwatch("id1");
    try {
      // will fail
      StopwatchFactory.getStopwatch("id1");

    } catch (IllegalArgumentException ex) {
      logger.info(" Error found in initialization");
    }
    recordTimeSimple(stopwatch);
    stopwatch.reset();
    recordTimeWithLaps(stopwatch);
    StopwatchFactory.getStopwatch("id2");
    StopwatchFactory.getStopwatch("id3");

    List<IStopwatch> stopwatches = StopwatchFactory.getStopwatches();
    for (IStopwatch aStopwatch : stopwatches) {
      logger.info(aStopwatch.getId());
    }
    testWeirdOpsOnStopwatch();
  }
}
