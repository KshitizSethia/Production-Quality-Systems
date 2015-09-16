package edu.nyu.pqs.stopwatch.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  private static ConcurrentMap<String, IStopwatch> cache = new ConcurrentHashMap<String, IStopwatch>();
  private static Object cacheLock = new Object();

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
    synchronized (cacheLock) {
      if (id == null) {
        throw new IllegalArgumentException("Error argument 'id' is null");
      }
      if (cache.containsKey(id)) {
        throw new IllegalArgumentException("Error stopwatch with id: " + id
            + " already exists");
      } else {
        IStopwatch stopwatch = new Stopwatch(id);
        cache.put(id, stopwatch);
        return stopwatch;
      }
    }
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of all created IStopwatch objects. Returns an empty list if
   *         no IStopwatches have been created.
   */
  public static List<IStopwatch> getStopwatches() {
    synchronized (cacheLock) {
      List<IStopwatch> stopwatches = new CopyOnWriteArrayList<IStopwatch>(
          cache.values());
      return stopwatches;
    }
  }
}
