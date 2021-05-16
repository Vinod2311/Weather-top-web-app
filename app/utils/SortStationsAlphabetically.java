package utils;

import models.Station;

import java.util.Comparator;

public class SortStationsAlphabetically implements Comparator<Station> {

  @Override
  public int compare(Station a, Station b) {
    return a.name.toUpperCase().compareTo(b.name.toUpperCase());
  }
}
