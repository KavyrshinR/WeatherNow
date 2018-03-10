package ru.kavyrshin.weathernow.data.repositories;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.util.WeatherSettings;


public class SettingsRepository implements ISettingsRepository {

    private AppDatabase database;


    @Inject
    public SettingsRepository(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Single<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings final int temperatureUnit) {

        return getWeatherSettings()
                .filter(new Predicate<WeatherSettings>() {
                    @Override
                    public boolean test(WeatherSettings weatherSettings) throws Exception {
                        return temperatureUnit != weatherSettings.getTemperatureUnit();
                    }
                })
                .flatMapSingle(new Function<WeatherSettings, SingleSource<WeatherSettings>>() {
                    @Override
                    public SingleSource<WeatherSettings> apply(WeatherSettings weatherSettings) throws Exception {
                        weatherSettings.setTemperatureUnit(temperatureUnit);
                        database.saveWeatherSettings(weatherSettings);
                        return Single.just(weatherSettings);
                    }
                });
    }

    @Override
    public Single<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings final int pressureUnit) {

        return getWeatherSettings()
                .filter(new Predicate<WeatherSettings>() {
                    @Override
                    public boolean test(WeatherSettings weatherSettings) throws Exception {
                        return pressureUnit != weatherSettings.getPressureUnit();
                    }
                })
                .flatMapSingle(new Function<WeatherSettings, SingleSource<WeatherSettings>>() {
                    @Override
                    public SingleSource<WeatherSettings> apply(WeatherSettings weatherSettings) throws Exception {
                        weatherSettings.setPressureUnit(pressureUnit);
                        database.saveWeatherSettings(weatherSettings);
                        return Single.just(weatherSettings);
                    }
                });

    }

    @Override
    public Single<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings final int windSpeedUnit) {

        return getWeatherSettings()
                .filter(new Predicate<WeatherSettings>() {
                    @Override
                    public boolean test(WeatherSettings weatherSettings) throws Exception {
                        return windSpeedUnit != weatherSettings.getWindSpeedUnit();
                    }
                })
                .flatMapSingle(new Function<WeatherSettings, SingleSource<WeatherSettings>>() {
                    @Override
                    public SingleSource<WeatherSettings> apply(WeatherSettings weatherSettings) throws Exception {
                        weatherSettings.setWindSpeedUnit(windSpeedUnit);
                        database.saveWeatherSettings(weatherSettings);
                        return Single.just(weatherSettings);
                    }
                });

    }

    @Override
    public Single<WeatherSettings> getWeatherSettings() {
        return Single.just(database.getWeatherSettings());
    }
}