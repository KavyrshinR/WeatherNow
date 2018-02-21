package ru.kavyrshin.weathernow.data.repositories;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;


public class SettingsRepository implements ISettingsRepository {

    private WeatherSettings weatherSettings;
    private AppDatabase database;


    @Inject
    public SettingsRepository(WeatherSettings weatherSettings, AppDatabase database) {
        this.weatherSettings = weatherSettings;
        this.database = database;
    }

    @Override
    public Observable<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings final int temperatureUnit) {
        return Observable.create(new Action1<Emitter<WeatherSettings>>() {
            @Override
            public void call(Emitter<WeatherSettings> weatherSettingsEmitter) {
                if (weatherSettings.getTemperatureUnit() != temperatureUnit) {
                    weatherSettings.setTemperatureUnit(temperatureUnit);
                    database.saveWeatherSettings(weatherSettings);
                    weatherSettingsEmitter.onNext(weatherSettings);
                }
                weatherSettingsEmitter.onCompleted();
            }
        }, Emitter.BackpressureMode.BUFFER);
    }

    @Override
    public Observable<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings final int pressureUnit) {
        return Observable.create(new Action1<Emitter<WeatherSettings>>() {
            @Override
            public void call(Emitter<WeatherSettings> weatherSettingsEmitter) {
                if (weatherSettings.getPressureUnit() != pressureUnit) {
                    weatherSettings.setPressureUnit(pressureUnit);
                    database.saveWeatherSettings(weatherSettings);
                    weatherSettingsEmitter.onNext(weatherSettings);
                }
                weatherSettingsEmitter.onCompleted();
            }
        }, Emitter.BackpressureMode.BUFFER);
    }

    @Override
    public Observable<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings final int windSpeedUnit) {
        return Observable.create(new Action1<Emitter<WeatherSettings>>() {
            @Override
            public void call(Emitter<WeatherSettings> weatherSettingsEmitter) {
                if (weatherSettings.getWindSpeedUnit() != windSpeedUnit) {
                    weatherSettings.setWindSpeedUnit(windSpeedUnit);
                    database.saveWeatherSettings(weatherSettings);
                    weatherSettingsEmitter.onNext(weatherSettings);
                }
                weatherSettingsEmitter.onCompleted();
            }
        }, Emitter.BackpressureMode.BUFFER);
    }

    @Override
    public Observable<WeatherSettings> getWeatherSettings() {
        return Observable.just(weatherSettings);
    }
}