package ru.kavyrshin.weathernow.data.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.kavyrshin.weathernow.BuildConfig;
import ru.kavyrshin.weathernow.data.api.ApiTimeZone;
import ru.kavyrshin.weathernow.data.api.ApiWeather;
import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.data.exception.CustomException;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.MainStationModel;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.domain.models.TimeZone;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class StationsRepository implements IStationsRepository {

    private ApiWeather apiWeather;
    private ApiTimeZone apiTimeZone;
    private AppDatabase database;

    private List<StationListElement> stationListElements = new ArrayList<>();

    @Inject
    public StationsRepository(@Named("apiWeather") ApiWeather apiWeather,
                              @Named("apiTimeZone") ApiTimeZone apiTimeZone,
                              AppDatabase database) {
        this.apiWeather = apiWeather;
        this.apiTimeZone = apiTimeZone;
        this.database = database;
    }

    @Override
    public Observable<List<StationListElement>> getStationsArround(double latitude, double longitude, int countStations) {
        return apiWeather.getStationArround(latitude, longitude, countStations, BuildConfig.API_KEY)
                .flatMap(new Func1<MainStationModel, Observable<List<StationListElement>>>() {
                    @Override
                    public Observable<List<StationListElement>> call(MainStationModel mainStationModel) {
                        if ((mainStationModel != null) && (!mainStationModel.getList().isEmpty())) {
                            StationsRepository.this.stationListElements.clear();
                            StationsRepository.this.stationListElements.addAll(mainStationModel.getList());
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

    private StationListElement getStationById(int apiCityId) {
        for (StationListElement item : stationListElements) {
            if (apiCityId == item.getId()) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Observable<CacheCity> saveFavouriteStation(final int apiCityId) {
        final StationListElement element = getStationById(apiCityId);

        String coordinate = element.getCoord().getLat() + "," + element.getCoord().getLon();
        return apiTimeZone.getTimeZoneByCoordinate(coordinate, element.getDt(), BuildConfig.G_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<TimeZone, Observable<CacheCity>>() {
                    @Override
                    public Observable<CacheCity> call(TimeZone timeZone) {
                        CacheCity cacheCity = new CacheCity();
                        cacheCity.setId(apiCityId);
                        cacheCity.setName(element.getName());
                        cacheCity.setUtcOffset(timeZone.getRawOffset());
                        cacheCity.setDstOffset(timeZone.getDstOffset());
                        return database.saveCacheCity(cacheCity);
                    }
                });
    }

    @Override
    public void deleteFavouriteStation(int cityId) {
        database.deleteCacheCity(cityId);
    }
}
