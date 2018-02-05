package ru.kavyrshin.weathernow.di.global;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.kavyrshin.weathernow.MyApplication;

@Module
public class ApplicationModule {

    @Provides
    public static Context context(MyApplication application) {
        return application;
    }
}