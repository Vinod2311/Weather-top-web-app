package utils;

import models.Reading;
import models.Station;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StationAnalytics {





    public static void celsiusToFahrenheit(Reading reading,double temperature){
        double temperatureInFahrenheit = (32 + (9/5)*(temperature));
        reading.setTemperatureFahrenheit(temperatureInFahrenheit);
    }



    public static void windChill(Reading reading,double temperature,double windSpeed){
        double partA= 13.12 + 0.6215*(temperature);
        double partB= (-11.37+0.3965*temperature)*(Math.pow(windSpeed,0.16));
        double total= partA + partB;
        total = roundToTwoDecimal(total);
        reading.setWindChill(total);
    }



    public static void translateWeatherCode(Reading reading, int code){
        HashMap<Integer,String> weatherCode = new HashMap<>();
        weatherCode.put(100,"Clear");
        weatherCode.put(200,"Partial clouds");
        weatherCode.put(300,"Cloudy");
        weatherCode.put(400,"Light Showers");
        weatherCode.put(500,"Heavy Showers");
        weatherCode.put(600,"Rain");
        weatherCode.put(700,"Snow");
        weatherCode.put(800,"Thunder");
        for (Integer intCode: weatherCode.keySet()){
            if (intCode == code){
                reading.setTranslatedWeatherCode(weatherCode.get(intCode));
            }
        }
    }

    public static boolean isBetween(double x,double lower, double upper){
        return (lower <= x && x <= upper);
    }

    public static void beaufortConversion(Reading reading, double windSpeed){
        if (isBetween(windSpeed,0,1)){
            reading.setWindBeaufort(0);
        }else if (isBetween(windSpeed,1,5)){
            reading.setWindBeaufort(1);
        }else if (isBetween(windSpeed,6,11)){
            reading.setWindBeaufort(2);
        }else if (isBetween(windSpeed,12,19)){
            reading.setWindBeaufort(3);
        }else if (isBetween(windSpeed,20,28)){
            reading.setWindBeaufort(4);
        }else if (isBetween(windSpeed,29,38)){
            reading.setWindBeaufort(5);
        }else if (isBetween(windSpeed,39,49)){
            reading.setWindBeaufort(6);
        }else if (isBetween(windSpeed,50,61)){
            reading.setWindBeaufort(7);
        }else if (isBetween(windSpeed,62,74)){
            reading.setWindBeaufort(8);
        }else if (isBetween(windSpeed,75,88)){
            reading.setWindBeaufort(9);
        }else if (isBetween(windSpeed,89,102)){
            reading.setWindBeaufort(10);
        }else if (isBetween(windSpeed,103,117)){
            reading.setWindBeaufort(11);
        }
    }

    public static void windDirectionCompass(Reading reading,double windDirection){
        ArrayList<String> compass = new ArrayList<>();
        compass.add("North");
        compass.add("North North-East");
        compass.add("North-East");
        compass.add("East North-East");
        compass.add("East");
        compass.add("East South-East");
        compass.add("South-East");
        compass.add("South South-East");
        compass.add("South");
        compass.add("South South-West");
        compass.add("South-West");
        compass.add("West South-West");
        compass.add("West");
        compass.add("West North-West");
        compass.add("North-West");
        compass.add("North North-West");
        double initialRange = 33.75;
        int count = 1;
        for (double i =11.25; i<348.75;i+=22.5){
            if (windDirection >= i && windDirection <=initialRange){
                reading.setWindDirectionCompass(compass.get(count));

            } else if ( (windDirection>=0 && windDirection<=11.25) ||
            (windDirection>= 348.75 && windDirection<= 360) ){
                reading.setWindDirectionCompass(compass.get(0));
            } else{
                count ++;
                initialRange += 22.5;
            }
        }
    }

    public static double roundToTwoDecimal(double input){

        return (double) Math.round(input*100)/100;
    }

}
