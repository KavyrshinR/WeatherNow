package ru.kavyrshin.weathernow.domain.interactors;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;

public class AddStationInteractor {

    private IStationsRepository stationsRepository;
    private ISettingsRepository settingsRepository;


    @Inject
    public AddStationInteractor(IStationsRepository stationsRepository, ISettingsRepository settingsRepository) {
        this.stationsRepository = stationsRepository;
        this.settingsRepository = settingsRepository;
    }

    public Single<List<StationListElement>> getStationsArround(final double latitude, final double longitude) {
        return settingsRepository.getWeatherSettings()
                .flatMap(new Function<WeatherSettings, SingleSource<List<StationListElement>>>() {
                @Override
                public Single<List<StationListElement>> apply(final WeatherSettings weatherSettings) {
                    return stationsRepository.getStationsArround(latitude, longitude, 10)
                            .flatMap(new Function<List<StationListElement>, SingleSource<List<StationListElement>>>() {
                                @Override
                                public Single<List<StationListElement>> apply(List<StationListElement> stationListElements) {
                                    for (int i = 0; i < stationListElements.size(); i++) {
                                        Utils.convertWeatherUnit(stationListElements.get(i), weatherSettings);
                                    }

                                    return Single.just(stationListElements);
                                }
                            })
                            .subscribeOn(Schedulers.io());
            }
        });
    }

    public Single<CacheCity> saveStation(int cityId) {
        return stationsRepository.saveFavouriteStation(cityId)
                .subscribeOn(Schedulers.io());
    }
}