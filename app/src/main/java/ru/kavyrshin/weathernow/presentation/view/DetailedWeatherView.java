package ru.kavyrshin.weathernow.presentation.view;


import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.util.WeatherSettings;

public interface DetailedWeatherView extends BaseView {

    void showError(int textRes);
    void showWeather(MainWeatherModel weatherModel, WeatherSettings weatherSettings);

}