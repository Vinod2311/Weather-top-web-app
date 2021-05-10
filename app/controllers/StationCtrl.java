package controllers;

import java.util.List;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        Station station = Station.findById(id);
        Logger.info ("Station id = " + id);
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
        render("/station.html", station);
    }

    public static void addReading(Long id, String date,int code, double temperature, int windSpeed, int windDirection, int pressure)
    {
        Reading reading = new Reading(date,code,temperature,windSpeed,windDirection,pressure);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        render("/station.html", station);
    }
}
