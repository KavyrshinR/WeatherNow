package ru.kavyrshin.weathernow.di.global;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.util.WeatherSettings;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase() {
        return new AppDatabase();
    }

    @Provides
    WeatherSettings provideUserUtilSettings(AppDatabase appDatabase) {
        return appDatabase.getWeatherSettings();
    }
}