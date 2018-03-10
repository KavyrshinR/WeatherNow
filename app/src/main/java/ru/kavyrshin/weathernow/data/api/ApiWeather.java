package ru.kavyrshin.weathernow.data.api;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kavyrshin.weathernow.domain.models.MainStationModel;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;

public interface ApiWeather {

    @GET("find")
    Single<MainStationModel> getStationArround(@Query("lat") double latitude,
                                               @Query("lon") double longitude,
                                               @Query("cnt") int countStations,
                                               @Query("APPID") String ApiId);

    @GET("forecast/daily")
    Single<MainWeatherModel> getWeatherByIdCity(@Query("id") int cityId,
                                                    @Query("cnt") int countDays,
                                                    @Query("APPID") String ApiId);
}