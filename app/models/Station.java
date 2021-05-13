package models;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
import utils.StationAnalytics;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends Model {
    public String name;
    public double latitude;
    public double longitude;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings;


    /**
     * Constructor to create station with a name
     */

    public Station(String name, double lat, double lng){
        this.name = name;
        lat = StationAnalytics.roundToTwoDecimal(lat);
        lng= StationAnalytics.roundToTwoDecimal(lng);
        this.latitude = lat;
        this.longitude = lng;
        readings = new ArrayList<>();
    }



}
