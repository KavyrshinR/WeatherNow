package ru.kavyrshin.weathernow.util;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeatherSettings extends RealmObject {

    @Retention(RetentionPolicy.CLASS)
    @IntDef({CELSIUS_UNIT, KELVIN_UNIT, FAHRENHEIT_UNIT})
    public @interface TemperatureSettings{}
    public static final int CELSIUS_UNIT = 11;
    public static final int KELVIN_UNIT = 12;
    public static final int FAHRENHEIT_UNIT = 13;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({H_PA_UNIT, MM_OF_MERCURY_UNIT})
    public @interface PressureSettings{}
    public static final int H_PA_UNIT = 21;
    public static final int MM_OF_MERCURY_UNIT = 22;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({M_PER_SEC_UNIT, MI_PER_HOUR_UNIT})
    public @interface SpeedSettings{}
    public static final int M_PER_SEC_UNIT = 31;
    public static final int MI_PER_HOUR_UNIT = 32;

    @PrimaryKey
    private int id = 1;

    private int temperatureUnit = CELSIUS_UNIT;
    private int pressureUnit = MM_OF_MERCURY_UNIT;
    private int windSpeedUnit = M_PER_SEC_UNIT;


    public void setTemperatureUnit(@TemperatureSettings int temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setPressureUnit(@PressureSettings int pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public void setWindSpeedUnit(@SpeedSettings int windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    @TemperatureSettings
    public int getTemperatureUnit() {
        return temperatureUnit;
    }

    @PressureSettings
    public int getPressureUnit() {
        return pressureUnit;
    }

    @SpeedSettings
    public int getWindSpeedUnit() {
        return windSpeedUnit;
    }



}