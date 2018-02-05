package ru.kavyrshin.weathernow.presentation.view;


import ru.kavyrshin.weathernow.entity.WeatherListElement;
import ru.kavyrshin.weathernow.util.WeatherSettings;

public interface DetailedWeatherView extends BaseView {

    void showError(int textRes);
    void showWeather(WeatherListElement weather, String cityName, WeatherSettings weatherSettings);

}