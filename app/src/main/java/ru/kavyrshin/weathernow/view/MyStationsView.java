package ru.kavyrshin.weathernow.view;

import java.util.List;

import ru.kavyrshin.weathernow.entity.WeatherListElement;

public interface MyStationsView extends BaseView {

    void showMyStations(List<WeatherListElement> weatherListElements);
}