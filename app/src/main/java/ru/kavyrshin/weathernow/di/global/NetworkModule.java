package ru.kavyrshin.weathernow.di.global;


import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kavyrshin.weathernow.data.api.ApiTimeZone;
import ru.kavyrshin.weathernow.data.api.ApiWeather;

@Module
public class NetworkModule {

    private static final String BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/";
    private static final String BASE_URL_TIMEZONE = "https://maps.googleapis.com/maps/api/timezone/";

    public static int countStationArround = 10;

    @Provides
    @Singleton
    static Gson gson () {
        return new Gson();
    }

    @Provides
    static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Named("apiWeather")
    @Singleton
    static ApiWeather apiWeather(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_WEATHER)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiWeather.class);

    }

    @Provides
    @Named("apiTimeZone")
    @Singleton
    static ApiTimeZone apiTimeZone(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_TIMEZONE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiTimeZone.class);

    }
}