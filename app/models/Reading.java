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
    public double windDirection;
    public double pressure;
    public String translatedWeatherCode;
    public double temperatureFahrenheit;
    public double windChill;
    public int windBeaufort;
    public String windDirectionCompass;


    /**
     * Constructor to create reading
     */

    public Reading(Date date,int code, double temp, double windSpeed, double windDirection, double pressure){
        this.code = code;
        this.temperature = temp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.date = date;
    }

    public void setTranslatedWeatherCode(String code){
        this.translatedWeatherCode = code;
    }

    public void setWindChill(Double chill){
        this.windChill = chill;
    }

    public void setWindBeaufort(int windBeaufort){
        this.windBeaufort = windBeaufort;
    }

    public void setTemperatureFahrenheit(Double temp){
        this.temperatureFahrenheit = temp;
    }

    public void setWindDirectionCompass(String windDirection){
        this.windDirectionCompass = windDirection;
    }
}
