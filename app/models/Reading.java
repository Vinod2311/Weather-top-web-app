package models;
import javax.persistence.Entity;

import org.joda.time.DateTime;
import play.db.jpa.Model;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Reading extends Model {
    public Date date;
    public int code;
    public double temperature;
    public double windSpeed;
    public int windDirection;
    public int pressure;

    /**
     * Constructor to create reading
     */

    public Reading(Date date,int code, double temp, double windSpeed, int windDirection, int pressure){
        this.code = code;
        this.temperature = temp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.date = date;
    }
}
