package controllers;

import models.Member;
import play.Logger;
import play.db.jpa.GenericModel;
import play.mvc.Controller;
import models.Reading;
import models.Station;
import utils.StationAnalytics;

import java.util.List;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = member.stations;
    for (Station station: stations){
      station.latestReading = StationAnalytics.fillLatestReading(station);
      StationAnalytics.performStationAnalytics(station);
    }
    render ("dashboard.html", stations,member);
  }

  public void addStation (String name, double lat, double lng)
  {
    Member member = Accounts.getLoggedInMember();
    Station station = new Station(name, lat, lng);
    Logger.info ("Adding a new station called " + name);
    member.stations.add(station);
    member.save();
    redirect ("/dashboard");
  }

  public void deleteStation(Long id){
    Logger.info("Deleting station");
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    redirect("/dashboard");
  }
}
