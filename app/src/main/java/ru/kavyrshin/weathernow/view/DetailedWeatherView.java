package ru.kavyrshin.weathernow.view;


import ru.kavyrshin.weathernow.entity.MainWeatherModel;

public interface DetailedWeatherView extends BaseView {

    void showWeather(MainWeatherModel weatherModel);

}