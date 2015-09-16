package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * Stopwatch implementation, do not use this directly! <br/>
 * This doesn't need to be visible as part of the API, only the factory needs
 * access to it.
 * 
 * @author Kshitiz
 *
 */
// TODO this class is package private => part of API, fix this
final class Stopwatch implements IStopwatch {

  private static enum State {
    Started, Stopped
  };

  // instance variables
  private String          id;
  private long            lapStartTime;
  private ArrayList<Long> lapTimes;
  private State           state;

  // create a lock on a private variable instead of on "this" as "this" can be
  // locked by code having a reference to the object.
  private Object          lockStopwatch;

  // TODO this constructor is visible in package => part of API, fix it
  Stopwatch(String id) {
    this.id = id;
    this.lockStopwatch = new Object();
    // started = false;
    reset();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void start() {
    // get time outside synchronized block:
    // 1) doesn't relinquish control to external code in synchronized block
    // 2) time is more accurate if some time is spent waiting for lock
    long entryTime = getTimeInMilliseconds();
    synchronized (lockStopwatch) {
      if (state != State.Stopped) {
        throw new IllegalStateException("Stopwatch is running");
      }
      state = State.Started;
      lapStartTime = entryTime;
    }
  }

  @Override
  public void lap() {
    long entryTime = getTimeInMilliseconds();
    synchronized (lockStopwatch) {
      if (state != State.Started) {
        throw new IllegalStateException("Stopwatch isn't running");
      }
      lapTimes.add(entryTime - lapStartTime);
      lapStartTime = entryTime;
    }
  }

  private long getTimeInMilliseconds() {
    return Math.round(System.nanoTime() / 1000000);
  }

  @Override
  public void stop() {
    long entryTime = getTimeInMilliseconds();
    synchronized (lockStopwatch) {
      if (state != State.Started) {
        throw new IllegalStateException("Stopwatch isn't running");
      }
      lap();
      state = State.Stopped;
    }
  }

  @Override
  public void reset() {
    synchronized (lockStopwatch) {
      lapStartTime = 0;
      if (lapTimes == null) {
        lapTimes = new ArrayList<Long>();
      } else {
        lapTimes.clear();
      }
      state = State.Stopped;
    }
  }

  @Override
  public List<Long> getLapTimes() {
    ArrayList<Long> results;
    synchronized (lockStopwatch) {
      results = new ArrayList<Long>(lapTimes);
    }
    return results;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Stopwatch)) {
      return false;
    }
    Stopwatch other = (Stopwatch) obj;
    return other.getId().equals(this.getId());
  }

  @Override
  public int hashCode() {
    return 31 + (17 * getId().hashCode());
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    synchronized (lockStopwatch) {
      result.append("ID: ");
      result.append(getId());
      result.append("; LapTimes: ");
      result.append(getLapTimes().toString());
    }
    return result.toString();
  }
}
