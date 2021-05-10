package models;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Reading extends Model {
    public String date;
    public int code;
    public double temperature;
    public int windSpeed;
    public int windDirection;
    public int pressure;

    /**
     * Constructor to create reading
     */

    public Reading(String date,int code, double temp, int windSpeed, int windDirection, int pressure){
        this.code = code;
        this.temperature = temp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.date = date;
    }
}
