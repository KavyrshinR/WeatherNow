package ru.kavyrshin.weathernow.data.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kavyrshin.weathernow.domain.models.TimeZone;
import rx.Observable;

public interface ApiTimeZone {

    @GET("json")
    Observable<TimeZone> getTimeZoneByCoordinate(@Query("location") String location,
                                                 @Query("timestamp") long timestamp,
                                                 @Query("key") String key);
}