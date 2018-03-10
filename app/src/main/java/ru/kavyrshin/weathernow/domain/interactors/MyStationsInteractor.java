package ru.kavyrshin.weathernow.domain.interactors;


import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IWeatherRepository;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;

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
                .flatMapObservable(new Function<List<CacheCity>, Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
                    @Override
                    public Observable<Pair<DataSource, List<MainWeatherModel>>> apply(final List<CacheCity> cacheCities) {

                        return settingsRepository.getWeatherSettings()
                                .flatMapObservable(new Function<WeatherSettings, Observable<Pair<DataSource, List<MainWeatherModel>>>>() {
                                    @Override
                                    public Observable<Pair<DataSource, List<MainWeatherModel>>> apply(final WeatherSettings weatherSettings) {
                                        return weatherRepository.getWeather(cacheCities)
                                                .subscribeOn(Schedulers.io())
                                                .concatMap(new Function<Pair<DataSource,List<MainWeatherModel>>,
                                                        ObservableSource<Pair<DataSource,List<MainWeatherModel>>>>() {
                                                    @Override
                                                    public Observable<Pair<DataSource, List<MainWeatherModel>>> apply(Pair<DataSource, List<MainWeatherModel>> dataSourceListPair) {

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

    public Single<Boolean> deleteFavouriteStation(int cityId) {
        return Single.zip(
                stationsRepository.deleteFavouriteStation(cityId),
                weatherRepository.deleteWeatherByCityId(cityId),
                new BiFunction<Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean, Boolean aBoolean2) throws Exception {
                        return aBoolean && aBoolean2;
                    }
                }).subscribeOn(Schedulers.io());
    }


}