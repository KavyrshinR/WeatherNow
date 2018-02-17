package ru.kavyrshin.weathernow.domain.interactors;


import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.models.Temperature;
import ru.kavyrshin.weathernow.domain.models.WeatherListElement;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IWeatherRepository;
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
                            convertWeatherUnit(item, weatherSettings);
                        }

                        return Observable.just(dataSourceListPair);
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }

//    public Observable<Pair<DataSource, List<MainWeatherModel>>> getAllCachedWeather() {
//        return weatherRepository.getAllCachedWeather()
//                .subscribeOn(Schedulers.io());
//    }

    public void deleteFavouriteStation(int cityId) {
        stationsRepository.deleteFavouriteStation(cityId);
        weatherRepository.deleteWeatherByCityId(cityId);
    }

    public void convertWeatherUnit(MainWeatherModel mainWeatherModel, WeatherSettings weatherSettings) {
        ArrayList<WeatherListElement> weatherList = new ArrayList<>(mainWeatherModel.getList());
        for (WeatherListElement item : weatherList) {
            if (weatherSettings.getTemperatureUnit() == WeatherSettings.CELSIUS_UNIT) {
                Temperature temperature = item.getTemp();
                temperature.setDay(WeatherSettings.getCelsiusFromKelvin(temperature.getDay()));
                temperature.setEve(WeatherSettings.getCelsiusFromKelvin(temperature.getEve()));
                temperature.setMorn(WeatherSettings.getCelsiusFromKelvin(temperature.getMorn()));
                temperature.setNight(WeatherSettings.getCelsiusFromKelvin(temperature.getNight()));
                temperature.setMax(WeatherSettings.getCelsiusFromKelvin(temperature.getMax()));
                temperature.setMin(WeatherSettings.getCelsiusFromKelvin(temperature.getMin()));
            } else if (weatherSettings.getTemperatureUnit() == WeatherSettings.FAHRENHEIT_UNIT) {
                Temperature temperature = item.getTemp();
                temperature.setDay(WeatherSettings.getFahrenheitFromKelvin(temperature.getDay()));
                temperature.setEve(WeatherSettings.getFahrenheitFromKelvin(temperature.getEve()));
                temperature.setMorn(WeatherSettings.getFahrenheitFromKelvin(temperature.getMorn()));
                temperature.setNight(WeatherSettings.getFahrenheitFromKelvin(temperature.getNight()));
                temperature.setMax(WeatherSettings.getFahrenheitFromKelvin(temperature.getMax()));
                temperature.setMin(WeatherSettings.getFahrenheitFromKelvin(temperature.getMin()));
            }

            if (weatherSettings.getPressureUnit() == WeatherSettings.MM_OF_MERCURY_UNIT) {
                item.setPressure(WeatherSettings.getMmOfMercuryFromHpa(item.getPressure()));
            }

            if (weatherSettings.getWindSpeedUnit() == WeatherSettings.MI_PER_HOUR_UNIT) {
                item.setSpeed(WeatherSettings.getMiPerHourFromMeterPerSec(item.getSpeed()));
            }
        }
    }
}