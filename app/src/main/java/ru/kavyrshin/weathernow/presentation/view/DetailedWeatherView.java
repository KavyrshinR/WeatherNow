package ru.kavyrshin.weathernow.presentation.view;


import ru.kavyrshin.weathernow.domain.models.WeatherListElement;

public interface DetailedWeatherView extends BaseView {

    void showError(int textRes);
    void showWeather(WeatherListElement weather, String cityName);

}