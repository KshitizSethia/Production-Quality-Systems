package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/*
 * {@inheritDoc} 
 */
public class Stopwatch implements IStopwatch {
  /* stores an id that can be used to reference a stopwatch */
  String id;

  private Operations currentOp;
  private List<Long> laps = new ArrayList<Long>();
  private Long startTime;
  private Long pausedTime;
  /* used as a lock during each operation */
  private final Object lock = new Object();

  /* to differentiate between various state transitions of the object */
  private static enum Operations {
    START {
      @Override
      public String toString() {
        return "start()";
      }
    },
    LAP {
      @Override
      public String toString() {
        return "lap()";
      }
    },
    STOP {
      @Override
      public String toString() {
        return "stop()";
      }

    },
    RESET {
      @Override
      public String toString() {
        return "reset()";
      }
    }
  }

  /* For every new state this Enum must be updated */
  private static enum States {
    RUNNING {
      @Override
      public String toString() {
        return "Running";
      }
    },
    RESET {
      @Override
      public String toString() {
        return "Reset";
      }
    },
    PAUSED {
      @Override
      public String toString() {
        return "Paused";
      }
    };
  }

  /*
   * Meant to be a place holder for the state of the object. This could be
   * useful in the future.
   */
  private States state;

  Stopwatch(String id) {
    if (id == null) {
      throw new IllegalStateException("Error: Id is null");
    }
    this.id = id;
    this.state = States.RESET;
    this.currentOp = Operations.RESET;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void start() {
    synchronized (lock) {
      /* this is the current operation */
      currentOp = Operations.START;
      /* were we at a legal state before this method call */
      if (state == States.RUNNING) {
        throw new IllegalStateException(
            this.getErrorMessageForOperation(currentOp));
      } else {
        if (state == States.RESET) {
          startTime = new Date().getTime();
        } else if (state == States.PAUSED) {
          startTime = pausedTime;
          laps.remove(laps.size() - 1);
        }
        state = States.RUNNING;
      }
    }

  }

  @Override
  public void lap() {
    synchronized (lock) {

      currentOp = Operations.LAP;

      if (state != States.RUNNING) {
        throw new IllegalStateException(
            this.getErrorMessageForOperation(currentOp));
      } else {

        Long lapTime = new Date().getTime();
        laps.add(lapTime - startTime);
        startTime = lapTime;

      }

    }
  }

  /* generic error message helper */
  private String getErrorMessageForOperation(Operations operationName) {
    return "Error: Stopwatch " + this.id + " cannot perform "
        + operationName.toString() + " in " + this.state.toString() + " state ";
  }

  @Override
  public void stop() {
    synchronized (lock) {

      currentOp = Operations.STOP;

      if (state != States.RUNNING) {
        throw new IllegalStateException(
            this.getErrorMessageForOperation(currentOp));
      } else {
        pausedTime = new Date().getTime();
        laps.add(pausedTime - startTime);
        state = States.PAUSED;
      }

    }

  }

  @Override
  public void reset() {
    synchronized (lock) {

      currentOp = Operations.RESET;

      if (state != States.PAUSED) {
        throw new IllegalStateException(
            this.getErrorMessageForOperation(currentOp));
      } else {
        state = States.RESET;
        laps.clear();
        startTime = new Date().getTime();
      }
    }

  }

  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      List<Long> lapsCopy = new CopyOnWriteArrayList<Long>(laps);
      return lapsCopy;
    }
  }

}
