package ru.kavyrshin.weathernow.di.global;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.kavyrshin.weathernow.data.database.AppDatabase;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase() {
        return new AppDatabase();
    }

}