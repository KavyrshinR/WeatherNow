package ru.kavyrshin.weathernow.data.api;


import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kavyrshin.weathernow.domain.models.MainStationModel;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import rx.Observable;

public interface ApiWeather {

    @GET("find")
    Observable<MainStationModel> getStationArround(@Query("lat") double latitude,
                                                   @Query("lon") double longitude,
                                                   @Query("cnt") int countStations,
                                                   @Query("APPID") String ApiId);

    @GET("forecast/daily")
    Observable<MainWeatherModel> getWeatherByIdCity(@Query("id") int cityId,
                                                    @Query("cnt") int countDays,
                                                    @Query("APPID") String ApiId);
}