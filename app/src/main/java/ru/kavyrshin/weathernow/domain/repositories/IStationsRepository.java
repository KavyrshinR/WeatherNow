package ru.kavyrshin.weathernow.domain.repositories;

import java.util.List;

import io.reactivex.Single;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;

public interface IStationsRepository {
    Single<List<StationListElement>> getStationsArround(double latitude, double longitude, int countStations);
    Single<List<CacheCity>> getFavouriteStations();
    Single<CacheCity> saveFavouriteStation(int cityId);
    Single<Boolean> deleteFavouriteStation(int cityId);
}