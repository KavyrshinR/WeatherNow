package ru.kavyrshin.weathernow.view;

import java.util.List;

import ru.kavyrshin.weathernow.entity.MainWeatherModel;

public interface MyStationsView extends BaseView {

    void showMyStations(List<MainWeatherModel> weatherListElements);
}