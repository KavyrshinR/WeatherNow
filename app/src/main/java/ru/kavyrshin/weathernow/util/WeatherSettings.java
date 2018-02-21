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



}