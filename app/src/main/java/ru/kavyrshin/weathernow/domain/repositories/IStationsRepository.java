package ru.kavyrshin.weathernow.domain.repositories;

import java.util.List;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import rx.Observable;

public interface IStationsRepository {
    Observable<List<StationListElement>> getStationsArround(double latitude, double longitude, int countStations);
    Observable<List<CacheCity>> getFavouriteStations();
    Observable<CacheCity> saveFavouriteStation(int cityId);
    Observable<Boolean> deleteFavouriteStation(int cityId);
}