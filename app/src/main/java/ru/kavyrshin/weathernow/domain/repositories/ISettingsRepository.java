package ru.kavyrshin.weathernow.domain.repositories;


import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;

public interface ISettingsRepository {
    Observable<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings int temperatureUnit);
    Observable<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings int pressureUnit);
    Observable<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings int windSpeedUnit);
    Observable<WeatherSettings> getWeatherSettings();
}