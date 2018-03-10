package ru.kavyrshin.weathernow.domain.repositories;


import io.reactivex.Single;
import ru.kavyrshin.weathernow.util.WeatherSettings;

public interface ISettingsRepository {
    Single<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings int temperatureUnit);
    Single<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings int pressureUnit);
    Single<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings int windSpeedUnit);
    Single<WeatherSettings> getWeatherSettings();
}