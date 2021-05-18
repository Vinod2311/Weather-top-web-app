package controllers;

import models.Member;
import models.Station;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.mvc.Controller;
import utils.SortStationsAlphabetically;
import utils.StationAnalytics;

import java.util.Collections;
import java.util.List;

/**
 * Controller class to display each members dashboard(summary) page.
 */
public class Dashboard extends Controller {

  /**
   * Fetch the details of the member that is logged in.
   * Use the stored details to calculate trends and conversion in weather.
   * Display the stations and their summary.
   * Display the error page if this fails.
   */
  public void index() {
    try {
      Logger.info("Rendering Dashboard");
      Member member = Accounts.getLoggedInMember();
      List<Station> stations = member.stations;
      Collections.sort(stations, new SortStationsAlphabetically());
      for (Station station : stations) {
        station.latestReading = StationAnalytics.fillLatestReading(station);
        StationAnalytics.performStationAnalytics(station);
      }
      render("dashboard.html", stations, member);
    } catch (Exception e) {
      System.err.println("Caught Exception: " + e);
      render("error.html");
    }
  }

  /**
   * Renders error page given any path not already assigned
   *
   * @param path
   */
  public static void errorPage(String path) {
    render("error.html");
  }

  /**
   * Creates Station object with inputted details and saves to the database.
   *
   * @param name Station name
   * @param lat  Station latitude
   * @param lng  Station longitude
   */
  public void addStation(String name, double lat, double lng) {
    try {
      Member member = Accounts.getLoggedInMember();
      Station station = new Station(StringUtils.capitalize(name), lat, lng);
      Logger.info("Adding a new station called " + name);
      member.stations.add(station);
      member.save();
      redirect("/dashboard");
    } catch (Exception e) {
      System.err.println("Caught Exception: " + e);
      render("error.html");
    }
  }

  /**
   * Deletes station with inputted id from database.
   *
   * @param id Station id
   */
  public void deleteStation(Long id) {
    Logger.info("Deleting station");
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    redirect("/dashboard");
  }
}
