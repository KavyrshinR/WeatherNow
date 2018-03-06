package ru.kavyrshin.weathernow.domain.repositories;


import android.util.Pair;

import java.util.List;

import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import rx.Observable;

public interface IWeatherRepository {
    Observable<Pair<DataSource, List<MainWeatherModel>>> getWeather(final List<CacheCity> favouriteCities);
    Observable<Pair<DataSource, List<MainWeatherModel>>> getAllCachedWeather();
    Observable<MainWeatherModel> getWeatherByCityId(int cityId);
    Observable<Boolean> deleteWeatherByCityId(int cityId);
}