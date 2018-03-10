package ru.kavyrshin.weathernow.domain.repositories;


import android.util.Pair;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;

public interface IWeatherRepository {
    Observable<Pair<DataSource, List<MainWeatherModel>>> getWeather(final List<CacheCity> favouriteCities);
    Single<Pair<DataSource, List<MainWeatherModel>>> getAllCachedWeather();
    Single<MainWeatherModel> getWeatherByCityId(int cityId);
    Single<Boolean> deleteWeatherByCityId(int cityId);
}