package ru.kavyrshin.weathernow.model.api;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    public static String units = "metric";
    public static int countStationArround = 10;

    private static final String BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/";
    private static final String BASE_URL_TIMEZONE = "https://maps.googleapis.com/maps/api/timezone/";

    private static ApiWeather apiWeather;
    private static ApiTimeZone apiTimeZone;

    private ApiModule() {
    }

    public static ApiWeather getInstanceWeather() {
        if (apiWeather == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_WEATHER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiWeather = retrofit.create(ApiWeather.class);
        }

        return apiWeather;
    }

    public static ApiTimeZone getInstanceTimeZone() {
        if (apiTimeZone == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_TIMEZONE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiTimeZone = retrofit.create(ApiTimeZone.class);
        }

        return apiTimeZone;
    }

}