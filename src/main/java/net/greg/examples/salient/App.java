package net.greg.examples.salient;

import java.util.*;
import java.util.concurrent.*;

/*
  https://www.baeldung.com/java-hashmap-advanced
  https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html
*/
public final class App {

  private Map<String,String> map =
    new ConcurrentHashMap();

  private void go() {

    map.put("TAB1","A");
    map.put("TAB2","A");

    System.err.println("\nmap " + map);
    System.err.println("map.size() " + map.size());
    System.err.println("map.values() " + map.values());
    System.err.println("map.keySet() " + map.keySet());
    System.err.println("map.entrySet() " + map.entrySet() + "\n");
  }

  public static void main(String[] a) { new App().go(); }
}


/*

  In CHM, all operations are thread-safe, but retrieval does not entail locking (no support for locking the entire table).
  A CHM is fully interoperable w/ Hashtable in terms of thread-safety, but not in terms of synchronization.
  Dirty reads are possible.

  The hash API of a map is called internally to compute the final hash value from the initial hash value.
  The final hash value is an index in the internal ARRAY (a bucket location).
  The hash() method of Map looks like this:

    static final int hash(Object key) {
      int h;
      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


  The put() method method leverages the final hash value:

    public V put(K key, V value) {
      return putVal(hash(key), key, value, false, true);
    }

    ... the internal putVal() method is called, given the final hash value as its first argument.

    The key is used inside the putVal() method - a Map stores a key/value pair in the bucket location as a Map.Entry object
    - essentially, a SET (no duplicates allowed).

    When a null key is sent to a put(), it's assigned a final hash value of 0 (and becomes the first element of the underlying array),
    further, no hashing occurs (the hashCode API of the key is not invoked), this avoids an NPE.

    Using a key that was already used to store a value, put() returns the previous value for that key.

    Since Java 8, a balanced binary search tree - O(log n) - is used for collisions resolution, after the
    number of collisions in a given bucket exceeds a threshold.

    Two collisions (when there is a .75 load factor) results in a table resizing.
*/
