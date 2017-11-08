package ru.kavyrshin.weathernow.view;


import java.util.List;

import ru.kavyrshin.weathernow.entity.StationListElement;

public interface AddStationView extends BaseView {
    void showArroundStations(List<StationListElement> arroundStations);
    void stationAdded();
}