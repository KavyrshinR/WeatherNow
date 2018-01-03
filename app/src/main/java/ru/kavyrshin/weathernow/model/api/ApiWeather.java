package ru.kavyrshin.weathernow.model.api;


import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kavyrshin.weathernow.entity.MainStationModel;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import rx.Observable;

public interface ApiWeather {

    @GET("find")
    Observable<MainStationModel> getStationArround(@Query("lat") double latitude,
                                                   @Query("lon") double longitude,
                                                   @Query("cnt") int countStations,
                                                   @Query("units") String units,
                                                   @Query("APPID") String ApiId);

    @GET("forecast/daily")
    Observable<MainWeatherModel> getWeatherByIdCity(@Query("id") int cityId,
                                                    @Query("cnt") int countDays,
                                                    @Query("APPID") String ApiId);
}