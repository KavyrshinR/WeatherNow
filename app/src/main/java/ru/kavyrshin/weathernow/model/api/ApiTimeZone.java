package ru.kavyrshin.weathernow.model.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kavyrshin.weathernow.entity.TimeZone;
import rx.Observable;

public interface ApiTimeZone {

    @GET("json")
    Observable<TimeZone> getTimeZoneByCoordinate(@Query("location") String location,
                                                 @Query("timestamp") long timestamp,
                                                 @Query("key") String key);
}