package ru.kavyrshin.weathernow.domain.interactors;


import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AddStationInteractor {

    private WeatherSettings weatherSettings;
    private IStationsRepository stationsRepository;


    @Inject
    public AddStationInteractor(IStationsRepository stationsRepository,
                                WeatherSettings weatherSettings) {
        this.stationsRepository = stationsRepository;
        this.weatherSettings = weatherSettings;
    }

    public Observable<List<StationListElement>> getStationsArround(double latitude, double longitude) {
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

    public Observable<CacheCity> saveStation(int cityId) {
        return stationsRepository.saveFavouriteStation(cityId)
                .subscribeOn(Schedulers.io());
    }
}