package models;

import play.db.jpa.Model;
import utils.StationAnalytics;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Station class to store readings and weather trends
 */
@Entity
public class Station extends Model {
  public String name;
  public double latitude;
  public double longitude;
  public double maxTemp;
  public double minTemp;
  public double maxWindBeaufort;
  public double minWindBeaufort;
  public double maxPressure;
  public double minPressure;
  public String trendTemp;
  public String trendWindSpeed;
  public String trendPressure;
  public Reading latestReading;
  @OneToMany(cascade = CascadeType.ALL)
  public List<Reading> readings;


  /**
   * Constructor to create station with a name, latitude and longitude.
   */

  public Station(String name, double lat, double lng) {
    this.name = name;
    lat = StationAnalytics.roundToTwoDecimal(lat);
    lng = StationAnalytics.roundToTwoDecimal(lng);
    this.latitude = lat;
    this.longitude = lng;
    readings = new ArrayList<>();
  }


}
