package ru.kavyrshin.weathernow.domain.interactors;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.util.WeatherSettings;


public class SettingsInteractor {

    private ISettingsRepository settingsRepository;

    @Inject
    public SettingsInteractor(ISettingsRepository settingsRepository) {
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