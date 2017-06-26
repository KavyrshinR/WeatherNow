package ru.kavyrshin.weathernow.model.api;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    public static String units = "metric";
    public static int countStationArround = 10;

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static ApiWeather apiService;

    private ApiModule() {
    }

    public static ApiWeather getInstance() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiService = retrofit.create(ApiWeather.class);
        }

        return apiService;
    }

}