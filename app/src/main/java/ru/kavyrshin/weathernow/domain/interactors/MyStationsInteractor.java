package ru.kavyrshin.weathernow.domain.interactors;


import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IWeatherRepository;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MyStationsInteractor {

    private IWeatherRepository weatherRepository;
    private IStationsRepository stationsRepository;
    private WeatherSettings weatherSettings;

    @Inject
    public MyStationsInteractor(IWeatherRepository weatherRepository, IStationsRepository stationsRepository,
                                WeatherSettings weatherSettings) {
        this.weatherRepository = weatherRepository;
        this.stationsRepository = stationsRepository;
        this.weatherSettings = weatherSettings;
    }

    public Observable<Pair<DataSource, List<MainWeatherModel>>> getAllWeather() {
        return stationsRepository.getFavouriteStations()
                .concatMap(new Func1<List<CacheCity>, Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
            @Override
            public Observable<Pair<DataSource, List<MainWeatherModel>>> call(List<CacheCity> cacheCities) {

                if (cacheCities.isEmpty()) {
                    return Observable.just(null);
                }

                return weatherRepository.getWeather(cacheCities)
                        .startWith(weatherRepository.getAllCachedWeather())
                        .concatMap(new Func1<Pair<DataSource, List<MainWeatherModel>>, Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
                    @Override
                    public Observable<Pair<DataSource, List<MainWeatherModel>>> call(Pair<DataSource, List<MainWeatherModel>> dataSourceListPair) {
                        ArrayList<MainWeatherModel> weatherModels = new ArrayList<>(dataSourceListPair.second);

                        for (MainWeatherModel item : weatherModels) {
                            Utils.convertWeatherUnit(item, weatherSettings);
                        }

                        return Observable.just(dataSourceListPair);
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }

    public void deleteFavouriteStation(int cityId) {
        stationsRepository.deleteFavouriteStation(cityId);
        weatherRepository.deleteWeatherByCityId(cityId);
    }


}