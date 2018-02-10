package ru.kavyrshin.weathernow.presentation.view;


import java.util.List;

import ru.kavyrshin.weathernow.domain.models.StationListElement;

public interface AddStationView extends BaseView {
    void showArroundStations(List<StationListElement> arroundStations);
    void stationAdded();
}