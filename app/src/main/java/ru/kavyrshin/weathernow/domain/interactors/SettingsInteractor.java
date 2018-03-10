package ru.kavyrshin.weathernow.domain.interactors;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.kavyrshin.weathernow.data.repositories.SettingsRepository;
import ru.kavyrshin.weathernow.util.WeatherSettings;


public class SettingsInteractor {

    private SettingsRepository settingsRepository;

    @Inject
    public SettingsInteractor(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<WeatherSettings> saveTemperatureSettings(@WeatherSettings.TemperatureSettings int temperatureUnit) {
        return settingsRepository.saveTemperatureSettings(temperatureUnit)
                .subscribeOn(Schedulers.io());
    }

    public Single<WeatherSettings> savePressureSettings(@WeatherSettings.PressureSettings int pressureUnit) {
        return settingsRepository.savePressureSettings(pressureUnit)
                .subscribeOn(Schedulers.io());
    }

    public Single<WeatherSettings> saveWindSpeedSettings(@WeatherSettings.SpeedSettings int windSpeedUnit) {
        return settingsRepository.saveWindSpeedSettings(windSpeedUnit)
                .subscribeOn(Schedulers.io());
    }

    public Single<WeatherSettings> getWeatherSettings() {
        return settingsRepository.getWeatherSettings()
                .subscribeOn(Schedulers.io());
    }
}