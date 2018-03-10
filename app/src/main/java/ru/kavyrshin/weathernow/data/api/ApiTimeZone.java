package ru.kavyrshin.weathernow.data.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kavyrshin.weathernow.domain.models.TimeZone;

public interface ApiTimeZone {

    @GET("json")
    Single<TimeZone> getTimeZoneByCoordinate(@Query("location") String location,
                                             @Query("timestamp") long timestamp,
                                             @Query("key") String key);
}