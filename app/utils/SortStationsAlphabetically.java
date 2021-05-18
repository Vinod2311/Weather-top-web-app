package utils;

import models.Station;

import java.util.Comparator;

/**
 * This class can create a comparator object which compares two station
 * objects' names alphabetical order
 */
public class SortStationsAlphabetically implements Comparator<Station> {

  @Override
  public int compare(Station a, Station b) {
    return a.name.toUpperCase().compareTo(b.name.toUpperCase());
  }
}
