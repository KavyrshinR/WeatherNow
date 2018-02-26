package ru.kavyrshin.weathernow.domain.interactors;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.data.repositories.SettingsRepository;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.schedulers.Schedulers;

public class SettingsInteractor {

    private SettingsRepository settingsRepository;

    @Inject
    public SettingsInteractor(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Observable<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings int temperatureUnit) {
        return settingsRepository.saveTemperatureSettings(temperatureUnit)
                .subscribeOn(Schedulers.io());
    }

    public Observable<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings int pressureUnit) {
        return settingsRepository.savePressureSettings(pressureUnit)
                .subscribeOn(Schedulers.io());
    }

    public Observable<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings int windSpeedUnit) {
        return settingsRepository.saveWindSpeedSettings(windSpeedUnit)
                .subscribeOn(Schedulers.io());
    }

    public Observable<WeatherSettings> getWeatherSettings() {
        return settingsRepository.getWeatherSettings()
                .subscribeOn(Schedulers.io());
    }
}