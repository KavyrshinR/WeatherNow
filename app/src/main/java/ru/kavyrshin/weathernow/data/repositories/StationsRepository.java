package ru.kavyrshin.weathernow.data.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.kavyrshin.weathernow.BuildConfig;
import ru.kavyrshin.weathernow.data.api.ApiWeather;
import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.data.exception.CustomException;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.MainStationModel;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import rx.Observable;
import rx.functions.Func1;


public class StationsRepository implements IStationsRepository {

    private ApiWeather apiWeather;
    private AppDatabase database;

    @Inject
    public StationsRepository(@Named("apiWeather") ApiWeather apiWeather, AppDatabase database) {
        this.apiWeather = apiWeather;
        this.database = database;
    }

    @Override
    public Observable<List<StationListElement>> getStationsArround(double latitude, double longitude, int countStations) {
        return apiWeather.getStationArround(latitude, longitude, countStations, BuildConfig.API_KEY)
                .flatMap(new Func1<MainStationModel, Observable<List<StationListElement>>>() {
                    @Override
                    public Observable<List<StationListElement>> call(MainStationModel mainStationModel) {
                        if ((mainStationModel != null) && (!mainStationModel.getList().isEmpty())) {
                            return Observable.just(mainStationModel.getList());
                        } else {
                            return Observable.error(new CustomException(CustomException.SERVER_EXCEPTION, "Ошибка сервера"));
                        }
                    }
                });
    }

    @Override
    public Observable<List<CacheCity>> getFavouriteStations() {
        return Observable.just(database.getAllCacheCity());
    }

    @Override
    public void saveFavouriteStation(CacheCity cacheCity) {
        database.saveCacheCity(cacheCity);
    }

    @Override
    public void deleteFavouriteStation(int cityId) {
        database.deleteCacheCity(cityId);
    }
}
