package ru.kavyrshin.weathernow.view;


import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.util.WeatherSettings;

public interface DetailedWeatherView extends BaseView {

    void showWeather(MainWeatherModel weatherModel, WeatherSettings weatherSettings);

}