package ru.kavyrshin.weathernow.util;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeatherSettings extends RealmObject {

    public static final int CELSIUS_UNIT = 11;
    public static final int KELVIN_UNIT = 12;
    public static final int FAHRENHEIT_UNIT = 13;

    public static final int H_PA_UNIT = 21;
    public static final int MM_OF_MERCURY_UNIT = 22;

    public static final int M_PER_SEC_UNIT = 31;
    public static final int MI_PER_HOUR_UNIT = 32;


    public static final double KM_IN_MI = 0.62137119223733;
    public static final double hPa_IN_mmHg = 0.75006375541921;

    @PrimaryKey
    private int id = 1;

    private int temperatureUnit = CELSIUS_UNIT;
    private int pressureUnit = MM_OF_MERCURY_UNIT;
    private int windSpeedUnit = M_PER_SEC_UNIT;


    public void setTemperatureUnit(int temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setPressureUnit(int pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public void setWindSpeedUnit(int windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public int getTemperatureUnit() {
        return temperatureUnit;
    }

    public int getPressureUnit() {
        return pressureUnit;
    }

    public int getWindSpeedUnit() {
        return windSpeedUnit;
    }


    public static double getCelsiusFromKelvin(double kelvin) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("value kelvin is negative");
        }
        return kelvin - 273.15;
    }

    public static double getFahrenheitFromKelvin(double kelvin) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("value kelvin is negative");
        }
        return (kelvin * 1.8) - 459.67;
    }

    public static double getMiPerHourFromMeterPerSec(double meterPerSec) {
        double kmPerHour = meterPerSec * 60 * 60;
        kmPerHour = kmPerHour / 1000;

        double result = kmPerHour * KM_IN_MI;
        return result;
    }

    public static double getMmOfMercuryFromHpa(double mmOfMercury) {
        return mmOfMercury * hPa_IN_mmHg;
    }
}