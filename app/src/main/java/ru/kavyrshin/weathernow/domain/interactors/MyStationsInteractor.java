package ru.kavyrshin.weathernow.domain.interactors;


import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
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
    private ISettingsRepository settingsRepository;

    @Inject
    public MyStationsInteractor(IWeatherRepository weatherRepository, IStationsRepository stationsRepository,
                                ISettingsRepository settingsRepository) {
        this.weatherRepository = weatherRepository;
        this.stationsRepository = stationsRepository;
        this.settingsRepository = settingsRepository;
    }

    public Observable<Pair<DataSource, List<MainWeatherModel>>> getAllWeather() {
        return stationsRepository.getFavouriteStations()
                .concatMap(new Func1<List<CacheCity>, Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
            @Override
            public Observable<Pair<DataSource, List<MainWeatherModel>>> call(final List<CacheCity> cacheCities) {

                return settingsRepository.getWeatherSettings()
                        .flatMap(new Func1<WeatherSettings, Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
                            @Override
                            public Observable<Pair<DataSource, List<MainWeatherModel>>> call(final WeatherSettings weatherSettings) {
                                return weatherRepository.getWeather(cacheCities)
                                        .subscribeOn(Schedulers.io())
                                        .concatMap(new Func1<Pair<DataSource, List<MainWeatherModel>>,
                                                Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
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
                        });
            }
        });
    }

    public void deleteFavouriteStation(int cityId) {
        stationsRepository.deleteFavouriteStation(cityId);
        weatherRepository.deleteWeatherByCityId(cityId);
    }


}