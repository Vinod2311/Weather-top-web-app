package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

        Reading latestReading= null;

        latestReading = station.readings.get(station.readings.size() - 1);
        render("/station.html", station,latestReading);
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


    public static void addReading(Long id,int code, double temperature, int windSpeed, int windDirection, int pressure)
    {
        Date date = new Date();
        Reading reading = new Reading(date,code,temperature,windSpeed,windDirection,pressure);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        redirect("/stations/"+id);
    }

}
