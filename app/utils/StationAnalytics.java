package utils;

import models.Reading;
import models.Station;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A utility class includes methods used to calculate trends and conversion
 * in the weather reading
 */
public class StationAnalytics {


  /**
   * Fill the latest-reading field of the inputted station with converted
   * weather readings
   *
   * @param station
   * @return
   */
  public static Reading fillLatestReading(Station station) {
    if (station.readings.size() != 0) {
      Reading latestReading = station.readings.get(station.readings.size() - 1);
      translateWeatherCode(latestReading, latestReading.code);
      celsiusToFahrenheit(latestReading, latestReading.temperature);
      windChill(latestReading, latestReading.temperature, latestReading.windSpeed);
      beaufortConversion(latestReading, latestReading.windSpeed);
      windDirectionCompass(latestReading, latestReading.windDirection);
      return latestReading;
    }
    return null;
  }

  /**
   * Calculates and sets trends of the weather readings in a station
   *
   * @param station
   */
  public static void performStationAnalytics(Station station) {
    setMaxPressure(station);
    setMaxWindBeaufort(station);
    setMaxTemp(station);
    setMinPressure(station);
    setMinWindBeaufort(station);
    setMinTemp(station);
    trendInTemp(station);
    trendInPressure(station);
    trendInWindSpeed(station);
    latAndLngToTwoDecimal(station);
  }

  /**
   * Rounds longitude and latitude value of a station to 2 decimal places
   *
   * @param station
   */
  public static void latAndLngToTwoDecimal(Station station) {
    station.latitude = roundToTwoDecimal(station.latitude);
    station.longitude = roundToTwoDecimal(station.longitude);
  }


  public static void celsiusToFahrenheit(Reading reading, double temperature) {
    double temperatureInFahrenheit = (32 + (9 / 5) * (temperature));
    reading.setTemperatureFahrenheit(temperatureInFahrenheit);
  }


  public static void windChill(Reading reading, double temperature, double windSpeed) {
    double partA = 13.12 + 0.6215 * (temperature);
    double partB = (-11.37 + 0.3965 * temperature) * (Math.pow(windSpeed, 0.16));
    double total = partA + partB;
    total = roundToTwoDecimal(total);
    reading.setWindChill(total);
  }


  public static void translateWeatherCode(Reading reading, int code) {
    HashMap<Integer, String> weatherCode = new HashMap<>();
    weatherCode.put(100, "Clear");
    weatherCode.put(200, "Partial clouds");
    weatherCode.put(300, "Cloudy");
    weatherCode.put(400, "Light Showers");
    weatherCode.put(500, "Heavy Showers");
    weatherCode.put(600, "Rain");
    weatherCode.put(700, "Snow");
    weatherCode.put(800, "Thunder");
    for (Integer intCode : weatherCode.keySet()) {
      if (intCode == code) {
        reading.setTranslatedWeatherCode(weatherCode.get(intCode));
      }
    }
  }

  /**
   * Calculates if inputted value is between some lower and upper bound
   *
   * @param x     input
   * @param lower lower bound
   * @param upper upper bound
   * @return true if x is in range, false if x is not in range
   */
  public static boolean isBetween(double x, double lower, double upper) {
    return (lower <= x && x <= upper);
  }

  public static void beaufortConversion(Reading reading, double windSpeed) {
    if (windSpeed >= 0 && windSpeed < 1) {
      reading.setWindBeaufort(0);
    } else if (isBetween(windSpeed, 1, 5)) {
      reading.setWindBeaufort(1);
    } else if (isBetween(windSpeed, 6, 11)) {
      reading.setWindBeaufort(2);
    } else if (isBetween(windSpeed, 12, 19)) {
      reading.setWindBeaufort(3);
    } else if (isBetween(windSpeed, 20, 28)) {
      reading.setWindBeaufort(4);
    } else if (isBetween(windSpeed, 29, 38)) {
      reading.setWindBeaufort(5);
    } else if (isBetween(windSpeed, 39, 49)) {
      reading.setWindBeaufort(6);
    } else if (isBetween(windSpeed, 50, 61)) {
      reading.setWindBeaufort(7);
    } else if (isBetween(windSpeed, 62, 74)) {
      reading.setWindBeaufort(8);
    } else if (isBetween(windSpeed, 75, 88)) {
      reading.setWindBeaufort(9);
    } else if (isBetween(windSpeed, 89, 102)) {
      reading.setWindBeaufort(10);
    } else if (isBetween(windSpeed, 103, 117)) {
      reading.setWindBeaufort(11);
    }
  }

  public static void windDirectionCompass(Reading reading, double windDirection) {
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
    for (double i = 11.25; i < 348.75; i += 22.5) {
      if (windDirection >= i && windDirection <= initialRange) {
        reading.setWindDirectionCompass(compass.get(count));

      } else if ((windDirection >= 0 && windDirection <= 11.25) ||
          (windDirection >= 348.75 && windDirection <= 360)) {
        reading.setWindDirectionCompass(compass.get(0));
      } else {
        count++;
        initialRange += 22.5;
      }
    }
  }

  /**
   * Rounds any double input to 2 decimal places
   *
   * @param input
   * @return
   */
  public static double roundToTwoDecimal(double input) {

    return (double) Math.round(input * 100) / 100;
  }

  public static void setMaxTemp(Station station) {
    double maxValue = 0.0;
    if (station.readings.size() != 0) {
      maxValue = station.readings.get(0).temperature;
    }
    for (Reading reading : station.readings) {
      if (reading.temperature > maxValue) {
        maxValue = reading.temperature;
      }
    }
    station.maxTemp = roundToTwoDecimal(maxValue);
  }

  public static void setMinTemp(Station station) {
    double minValue = 0.0;
    if (station.readings.size() != 0) {
      minValue = station.readings.get(0).temperature;
    }
    for (Reading reading : station.readings) {
      if (reading.temperature < minValue) {
        minValue = reading.temperature;
      }
    }
    station.minTemp = roundToTwoDecimal(minValue);
  }

  public static void setMaxWindBeaufort(Station station) {
    double maxValue = 0.0;
    if (station.readings.size() != 0) {
      maxValue = station.readings.get(0).windBeaufort;
    }
    for (Reading reading : station.readings) {
      if (reading.windBeaufort > maxValue) {
        maxValue = reading.windBeaufort;
      }
    }
    station.maxWindBeaufort = roundToTwoDecimal(maxValue);
  }

  public static void setMinWindBeaufort(Station station) {
    double minValue = 11.0;
    if (station.readings.size() != 0) {
      minValue = station.readings.get(0).windBeaufort;
    }
    for (Reading reading : station.readings) {
      if (reading.windBeaufort < minValue) {
        minValue = reading.windBeaufort;
      }
    }
    station.minWindBeaufort = roundToTwoDecimal(minValue);
  }

  public static void setMaxPressure(Station station) {
    double maxValue = 0.0;
    if (station.readings.size() != 0) {
      maxValue = station.readings.get(0).pressure;
    }
    for (Reading reading : station.readings) {
      if (reading.pressure > maxValue) {
        maxValue = reading.pressure;
      }
    }
    station.maxPressure = roundToTwoDecimal(maxValue);
  }

  public static void setMinPressure(Station station) {
    double minValue = 0.0;
    if (station.readings.size() != 0) {
      minValue = station.readings.get(0).pressure;
    }
    for (Reading reading : station.readings) {
      if (reading.pressure < minValue) {
        minValue = reading.pressure;
      }
    }
    station.minPressure = roundToTwoDecimal(minValue);
  }


  public static void trendInTemp(Station station) {
    if (station.readings.size() >= 3) {
      double lastReading = station.readings.get(station.readings.size() - 1).temperature;
      double secondLastReading = station.readings.get(station.readings.size() - 2).temperature;
      double thirdLastReading = station.readings.get(station.readings.size() - 3).temperature;
      double differenceOne = lastReading - secondLastReading;
      double differenceTwo = secondLastReading - thirdLastReading;
      if (differenceOne < 0 && differenceTwo < 0) {
        station.trendTemp = "falling";
      } else if (differenceOne > 0 && differenceTwo > 0) {
        station.trendTemp = "rising";
      } else {
        station.trendTemp = "steady";
      }
    }
  }

  public static void trendInWindSpeed(Station station) {
    if (station.readings.size() >= 3) {
      double lastReading = station.readings.get(station.readings.size() - 1).windSpeed;
      double secondLastReading = station.readings.get(station.readings.size() - 2).windSpeed;
      double thirdLastReading = station.readings.get(station.readings.size() - 3).windSpeed;
      double differenceOne = lastReading - secondLastReading;
      double differenceTwo = secondLastReading - thirdLastReading;
      if (differenceOne < 0 && differenceTwo < 0) {
        station.trendWindSpeed = "falling";
      } else if (differenceOne > 0 && differenceTwo > 0) {
        station.trendWindSpeed = "rising";
      } else {
        station.trendWindSpeed = "steady";
      }
    }
  }

  public static void trendInPressure(Station station) {
    if (station.readings.size() >= 3) {
      double lastReading = station.readings.get(station.readings.size() - 1).pressure;
      double secondLastReading = station.readings.get(station.readings.size() - 2).pressure;
      double thirdLastReading = station.readings.get(station.readings.size() - 3).pressure;
      double differenceOne = lastReading - secondLastReading;
      double differenceTwo = secondLastReading - thirdLastReading;
      if (differenceOne < 0 && differenceTwo < 0) {
        station.trendPressure = "falling";
      } else if (differenceOne > 0 && differenceTwo > 0) {
        station.trendPressure = "rising";
      } else {
        station.trendPressure = "steady";
      }
    }
  }
}
