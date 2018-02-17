package ru.kavyrshin.weathernow.data;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import ru.kavyrshin.weathernow.data.repositories.StationsRepository;
import ru.kavyrshin.weathernow.data.repositories.WeatherRepository;
import ru.kavyrshin.weathernow.domain.repositories.IStationsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IWeatherRepository;

@Module
public class DataModule {

    @Reusable
    @Provides
    public IWeatherRepository provideWeatherRepository(WeatherRepository weatherRepository) {
        return weatherRepository;
    }

    @Reusable
    @Provides
    public IStationsRepository provideStationsRepository(StationsRepository stationsRepository) {
        return stationsRepository;
    }
}