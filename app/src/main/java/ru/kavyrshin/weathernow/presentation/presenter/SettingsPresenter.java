package ru.kavyrshin.weathernow.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.model.DataManager;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import ru.kavyrshin.weathernow.presentation.view.SettingsView;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private DataManager dataManager = DataManager.getInstance();

    private WeatherSettings weatherSettings;

    @Inject
    public SettingsPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getWeatherSettings();
    }

    public void saveTemperatureUnit(int temperatureUnit) {
        if (weatherSettings.getTemperatureUnit() != temperatureUnit) {
            weatherSettings.setTemperatureUnit(temperatureUnit);
            dataManager.saveWeatherSettings(weatherSettings);
            getViewState().showSettings(weatherSettings);
        }
    }

    public void savePressureUnit(int pressureUnit) {
        if (weatherSettings.getPressureUnit() != pressureUnit) {
            weatherSettings.setPressureUnit(pressureUnit);
            dataManager.saveWeatherSettings(weatherSettings);
            getViewState().showSettings(weatherSettings);
        }
    }

    public void saveWindSpeedUnit(int windSpeedUnit) {
        if (weatherSettings.getWindSpeedUnit() != windSpeedUnit) {
            weatherSettings.setWindSpeedUnit(windSpeedUnit);
            dataManager.saveWeatherSettings(weatherSettings);
            getViewState().showSettings(weatherSettings);
        }
    }

    public void getWeatherSettings() {
        weatherSettings = dataManager.getWeatherSettings();
        getViewState().showSettings(weatherSettings);
    }
}