package controllers;

import java.util.Date;


import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import utils.StationAnalytics;


public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        Station station = Station.findById(id);
        Logger.info ("Station id = " + id);
        station.latestReading = StationAnalytics.fillLatestReading(station);
        StationAnalytics.performStationAnalytics(station);
        render("/station.html", station);
    }

    public static void deleteReading (Long id, Long readingId)
    {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingId);
        Logger.info ("Removing reading");
        station.readings.remove(reading);
        station.save();
        reading.delete();
        redirect("/stations/"+id);
    }


    public static void addReading(Long id,int code, double temperature, double windSpeed, double windDirection, double pressure)
    {
        Date date = new Date();
        Reading reading = new Reading(date,code,temperature,windSpeed,windDirection,pressure);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        redirect("/stations/"+id);
    }

}
