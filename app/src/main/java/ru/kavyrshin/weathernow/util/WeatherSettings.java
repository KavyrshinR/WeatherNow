package ru.kavyrshin.weathernow.util;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static ru.kavyrshin.weathernow.util.Utils.CELSIUS_UNIT;
import static ru.kavyrshin.weathernow.util.Utils.MM_OF_MERCURY_UNIT;
import static ru.kavyrshin.weathernow.util.Utils.M_PER_SEC_UNIT;

public class WeatherSettings extends RealmObject {

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