package ru.kavyrshin.weathernow.util;

import java.util.ArrayList;

import ru.kavyrshin.weathernow.domain.models.MainWeatherInfo;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.domain.models.Temperature;
import ru.kavyrshin.weathernow.domain.models.WeatherListElement;

import static ru.kavyrshin.weathernow.util.WeatherSettings.CELSIUS_UNIT;
import static ru.kavyrshin.weathernow.util.WeatherSettings.FAHRENHEIT_UNIT;
import static ru.kavyrshin.weathernow.util.WeatherSettings.H_PA_UNIT;
import static ru.kavyrshin.weathernow.util.WeatherSettings.KELVIN_UNIT;
import static ru.kavyrshin.weathernow.util.WeatherSettings.MI_PER_HOUR_UNIT;
import static ru.kavyrshin.weathernow.util.WeatherSettings.MM_OF_MERCURY_UNIT;
import static ru.kavyrshin.weathernow.util.WeatherSettings.M_PER_SEC_UNIT;

public class Utils {

    public static final double KM_IN_MI = 0.62137119223733;
    public static final double mmHg_IN_hPa = 0.75006375541921;

    public static void convertWeatherUnit(MainWeatherModel mainWeatherModel, WeatherSettings weatherSettings) {
        ArrayList<WeatherListElement> weatherList = new ArrayList<>(mainWeatherModel.getList());
        for (WeatherListElement item : weatherList) {
            if (weatherSettings.getTemperatureUnit() == CELSIUS_UNIT) {
                Temperature temperature = item.getTemp();
                temperature.setDay(getCelsiusFromKelvin(temperature.getDay()));
                temperature.setEve(getCelsiusFromKelvin(temperature.getEve()));
                temperature.setMorn(getCelsiusFromKelvin(temperature.getMorn()));
                temperature.setNight(getCelsiusFromKelvin(temperature.getNight()));
                temperature.setMax(getCelsiusFromKelvin(temperature.getMax()));
                temperature.setMin(getCelsiusFromKelvin(temperature.getMin()));
            } else if (weatherSettings.getTemperatureUnit() == FAHRENHEIT_UNIT) {
                Temperature temperature = item.getTemp();
                temperature.setDay(getFahrenheitFromKelvin(temperature.getDay()));
                temperature.setEve(getFahrenheitFromKelvin(temperature.getEve()));
                temperature.setMorn(getFahrenheitFromKelvin(temperature.getMorn()));
                temperature.setNight(getFahrenheitFromKelvin(temperature.getNight()));
                temperature.setMax(getFahrenheitFromKelvin(temperature.getMax()));
                temperature.setMin(getFahrenheitFromKelvin(temperature.getMin()));
            }

            if (weatherSettings.getPressureUnit() == MM_OF_MERCURY_UNIT) {
                item.setPressure(getMmOfMercuryFromHpa(item.getPressure()));
            }

            if (weatherSettings.getWindSpeedUnit() == MI_PER_HOUR_UNIT) {
                item.setSpeed(getMiPerHourFromMeterPerSec(item.getSpeed()));
            }
        }
    }

    public static void convertWeatherUnit(WeatherListElement weatherListElement, WeatherSettings weatherSettings) {
        weatherListElement.setTemperatureUnit(KELVIN_UNIT);
        weatherListElement.setPressureUnit(H_PA_UNIT);
        weatherListElement.setWindSpeedUnit(M_PER_SEC_UNIT);

        if (weatherSettings.getTemperatureUnit() == CELSIUS_UNIT) {
            Temperature temperature = weatherListElement.getTemp();
            temperature.setDay(getCelsiusFromKelvin(temperature.getDay()));
            temperature.setEve(getCelsiusFromKelvin(temperature.getEve()));
            temperature.setMorn(getCelsiusFromKelvin(temperature.getMorn()));
            temperature.setNight(getCelsiusFromKelvin(temperature.getNight()));
            temperature.setMax(getCelsiusFromKelvin(temperature.getMax()));
            temperature.setMin(getCelsiusFromKelvin(temperature.getMin()));
            weatherListElement.setTemperatureUnit(CELSIUS_UNIT);
        } else if (weatherSettings.getTemperatureUnit() == FAHRENHEIT_UNIT) {
            Temperature temperature = weatherListElement.getTemp();
            temperature.setDay(getFahrenheitFromKelvin(temperature.getDay()));
            temperature.setEve(getFahrenheitFromKelvin(temperature.getEve()));
            temperature.setMorn(getFahrenheitFromKelvin(temperature.getMorn()));
            temperature.setNight(getFahrenheitFromKelvin(temperature.getNight()));
            temperature.setMax(getFahrenheitFromKelvin(temperature.getMax()));
            temperature.setMin(getFahrenheitFromKelvin(temperature.getMin()));
            weatherListElement.setTemperatureUnit(FAHRENHEIT_UNIT);
        }

        if (weatherSettings.getPressureUnit() == MM_OF_MERCURY_UNIT) {
            weatherListElement.setPressure(getMmOfMercuryFromHpa(weatherListElement.getPressure()));
            weatherListElement.setPressureUnit(MM_OF_MERCURY_UNIT);
        }

        if (weatherSettings.getWindSpeedUnit() == MI_PER_HOUR_UNIT) {
            weatherListElement.setSpeed(getMiPerHourFromMeterPerSec(weatherListElement.getSpeed()));
            weatherListElement.setWindSpeedUnit(MI_PER_HOUR_UNIT);
        }
    }

    public static void convertWeatherUnit(StationListElement stationListElement, WeatherSettings weatherSettings) {
        MainWeatherInfo item = stationListElement.getMain();

        if (weatherSettings.getTemperatureUnit() == CELSIUS_UNIT) {
            item.setTemp(getCelsiusFromKelvin(item.getTemp()));
            item.setTempMax(getCelsiusFromKelvin(item.getTempMax()));
            item.setTempMin(getCelsiusFromKelvin(item.getTempMin()));
        } else if (weatherSettings.getTemperatureUnit() == FAHRENHEIT_UNIT) {
            item.setTemp(getFahrenheitFromKelvin(item.getTemp()));
            item.setTempMax(getFahrenheitFromKelvin(item.getTempMax()));
            item.setTempMin(getFahrenheitFromKelvin(item.getTempMin()));
        }

        if (weatherSettings.getPressureUnit() == MM_OF_MERCURY_UNIT) {
            item.setPressure(getMmOfMercuryFromHpa(item.getPressure()));
        }

        if (weatherSettings.getWindSpeedUnit() == MI_PER_HOUR_UNIT) {
            stationListElement.getWind().setSpeed(getMiPerHourFromMeterPerSec(stationListElement.getWind().getSpeed()));
        }
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

    public static double getMmOfMercuryFromHpa(double hPa) {
        return hPa * mmHg_IN_hPa;
    }
}