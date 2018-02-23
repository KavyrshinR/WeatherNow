package ru.kavyrshin.weathernow.data.repositories;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.functions.Func1;


public class SettingsRepository implements ISettingsRepository {

    private AppDatabase database;


    @Inject
    public SettingsRepository(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings final int temperatureUnit) {

        return getWeatherSettings()
                .filter(new Func1<WeatherSettings, Boolean>() {
                    @Override
                    public Boolean call(WeatherSettings weatherSettings) {
                        return temperatureUnit != weatherSettings.getTemperatureUnit();
                    }
                })
                .flatMap(new Func1<WeatherSettings, Observable<WeatherSettings>>() {
                    @Override
                    public Observable<WeatherSettings> call(WeatherSettings weatherSettings) {
                        weatherSettings.setTemperatureUnit(temperatureUnit);
                        database.saveWeatherSettings(weatherSettings);
                        return Observable.just(weatherSettings);
                    }
                });
    }

    @Override
    public Observable<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings final int pressureUnit) {

        return getWeatherSettings()
                .filter(new Func1<WeatherSettings, Boolean>() {
                    @Override
                    public Boolean call(WeatherSettings weatherSettings) {
                        return pressureUnit != weatherSettings.getPressureUnit();
                    }
                })
                .flatMap(new Func1<WeatherSettings, Observable<WeatherSettings>>() {
                    @Override
                    public Observable<WeatherSettings> call(WeatherSettings weatherSettings) {
                        weatherSettings.setTemperatureUnit(pressureUnit);
                        database.saveWeatherSettings(weatherSettings);
                        return Observable.just(weatherSettings);
                    }
                });

    }

    @Override
    public Observable<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings final int windSpeedUnit) {

        return getWeatherSettings()
                .filter(new Func1<WeatherSettings, Boolean>() {
                    @Override
                    public Boolean call(WeatherSettings weatherSettings) {
                        return windSpeedUnit != weatherSettings.getWindSpeedUnit();
                    }
                })
                .flatMap(new Func1<WeatherSettings, Observable<WeatherSettings>>() {
                    @Override
                    public Observable<WeatherSettings> call(WeatherSettings weatherSettings) {
                        weatherSettings.setTemperatureUnit(windSpeedUnit);
                        database.saveWeatherSettings(weatherSettings);
                        return Observable.just(weatherSettings);
                    }
                });

    }

    @Override
    public Observable<WeatherSettings> getWeatherSettings() {
        return Observable.just(database.getWeatherSettings());
    }
}