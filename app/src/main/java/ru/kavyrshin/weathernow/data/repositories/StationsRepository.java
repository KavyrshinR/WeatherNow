package ru.kavyrshin.weathernow.data.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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
    public Single<List<StationListElement>> getStationsArround(double latitude, double longitude, int countStations) {
        return apiWeather.getStationArround(latitude, longitude, countStations, BuildConfig.API_KEY)
                .flatMap(new Function<MainStationModel, SingleSource<List<StationListElement>>>() {

                    @Override
                    public SingleSource<List<StationListElement>> apply(MainStationModel mainStationModel) throws Exception {
                        if ((mainStationModel != null) && (!mainStationModel.getList().isEmpty())) {
                            StationsRepository.this.stationListElements.clear();
                            StationsRepository.this.stationListElements.addAll(mainStationModel.getList());
                            return Single.just(mainStationModel.getList());
                        } else {
                            return Single.error(new CustomException(CustomException.SERVER_EXCEPTION, "Ошибка сервера"));
                        }
                    }
                });
    }

    @Override
    public Single<List<CacheCity>> getFavouriteStations() {
        return Single.just(database.getAllCacheCity());
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
    public Single<CacheCity> saveFavouriteStation(final int apiCityId) {
        final StationListElement element = getStationById(apiCityId);

        String coordinate = element.getCoord().getLat() + "," + element.getCoord().getLon();
        return apiTimeZone.getTimeZoneByCoordinate(coordinate, element.getDt(), BuildConfig.G_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<TimeZone, SingleSource<CacheCity>>() {

                    @Override
                    public SingleSource<CacheCity> apply(TimeZone timeZone) throws Exception {
                        CacheCity cacheCity = new CacheCity();
                        cacheCity.setId(apiCityId);
                        cacheCity.setName(element.getName());
                        cacheCity.setUtcOffset(timeZone.getRawOffset());
                        cacheCity.setDstOffset(timeZone.getDstOffset());
                        return Single.just(database.saveCacheCity(cacheCity));
                    }
                });
    }

    @Override
    public Single<Boolean> deleteFavouriteStation(int cityId) {
        return Single.just(database.deleteCacheCity(cityId));
    }
}
