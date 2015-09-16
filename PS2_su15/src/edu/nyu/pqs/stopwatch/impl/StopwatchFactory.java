package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {

  private StopwatchFactory() {
  }

  // TODO correct indentation manually below, Eclipse is doing something wrong
  // here
  private static ConcurrentHashMap<String, IStopwatch> stopwatches =
     new ConcurrentHashMap<String, IStopwatch>();

  /**
   * Creates and returns a new IStopwatch object
   * 
   * @param id
   *          The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException
   *           if <code>id</code> is empty, null, or already taken.
   */
  public static IStopwatch getStopwatch(String id) {

    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("id is null or empty");
    }

    IStopwatch result = null;
    // create a new watch only if one with same id doesn't exist
    synchronized (stopwatches) {
      if (!stopwatches.containsKey(id)) {
        // make sure the next line doesn't give back control to API client (by
        // disallowing inheritance)
        result = new Stopwatch(id);
        stopwatches.put(id, result);
      }
    }
    if (result == null) {
      throw new IllegalArgumentException("id already taken");
    }
    return result;
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of all creates IStopwatch objects. Returns an empty list if
   *         no IStopwatches have been created.</br>The list will be a snapshot
   *         copy, therefore any changes to the list will not be updated with
   *         {@link StopwatchFactory}.
   */
  public static List<IStopwatch> getStopwatches() {
    synchronized (stopwatches) {
      return new ArrayList<IStopwatch>(stopwatches.values());
    }
  }
}
