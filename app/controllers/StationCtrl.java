package controllers;

import java.util.Date;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import utils.StationAnalytics;

/**
 * Controller class to render each members unique station data.
 */
public class StationCtrl extends Controller {

  /**
   * Find the station with the inputted Id.
   * Calculate a summary of data(trends,conversions etc.) using the station
   * details. Render the station page.
   *
   * @param id Station id
   */
  public static void index(Long id) {
    try {
      Station station = Station.findById(id);
      Logger.info("Station id = " + id);
      station.latestReading = StationAnalytics.fillLatestReading(station);
      StationAnalytics.performStationAnalytics(station);
      render("/station.html", station);
    } catch (Exception e) {
      System.err.println("Caught Exception: " + e);
      render("error.html");
    }
  }

  /**
   * Deletes reading from database
   *
   * @param id        Station id
   * @param readingId Reading id
   */
  public static void deleteReading(Long id, Long readingId) {
    Station station = Station.findById(id);
    Reading reading = Reading.findById(readingId);
    Logger.info("Removing reading");
    station.readings.remove(reading);
    station.save();
    reading.delete();
    redirect("/stations/" + id);
  }

  /**
   * Adds a reading to the station with the inputted id.
   *
   * @param id            Station id
   * @param code          Weather code
   * @param temperature
   * @param windSpeed
   * @param windDirection
   * @param pressure
   */
  public static void addReading(Long id, int code, double temperature, double windSpeed, double windDirection, double pressure) {
    try {
      Date date = new Date();
      Reading reading = new Reading(date, code, temperature, windSpeed, windDirection, pressure);
      Station station = Station.findById(id);
      station.readings.add(reading);
      station.save();
      redirect("/stations/" + id);
    } catch (Exception e) {
      System.err.println("Caught Exception: " + e);
      render("error.html");
    }
  }

}
