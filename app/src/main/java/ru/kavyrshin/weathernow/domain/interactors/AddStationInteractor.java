package ru.kavyrshin.weathernow.domain.interactors;


import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AddStationInteractor {

    private IStationsRepository stationsRepository;
    private ISettingsRepository settingsRepository;


    @Inject
    public AddStationInteractor(IStationsRepository stationsRepository, ISettingsRepository settingsRepository) {
        this.stationsRepository = stationsRepository;
        this.settingsRepository = settingsRepository;
    }

    public Observable<List<StationListElement>> getStationsArround(final double latitude, final double longitude) {
        return settingsRepository.getWeatherSettings()
                .flatMap(new Func1<WeatherSettings, Observable<List<StationListElement>>>() {
            @Override
            public Observable<List<StationListElement>> call(final WeatherSettings weatherSettings) {
                return stationsRepository.getStationsArround(latitude, longitude, 10)
                        .flatMap(new Func1<List<StationListElement>, Observable<List<StationListElement>>>() {
                            @Override
                            public Observable<List<StationListElement>> call(List<StationListElement> stationListElements) {
                                for (int i = 0; i < stationListElements.size(); i++) {
                                    Utils.convertWeatherUnit(stationListElements.get(i), weatherSettings);
                                }

                                return Observable.just(stationListElements);
                            }
                        })
                        .subscribeOn(Schedulers.io());
            }
        });
    }

    public Observable<CacheCity> saveStation(int cityId) {
        return stationsRepository.saveFavouriteStation(cityId)
                .subscribeOn(Schedulers.io());
    }
}