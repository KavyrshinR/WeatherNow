package ru.kavyrshin.weathernow.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import ru.kavyrshin.weathernow.domain.interactors.SettingsInteractor;
import ru.kavyrshin.weathernow.presentation.view.SettingsView;
import ru.kavyrshin.weathernow.util.WeatherSettings;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SettingsInteractor settingsInteractor;

    @Inject
    public SettingsPresenter(SettingsInteractor settingsInteractor) {
        this.settingsInteractor = settingsInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getWeatherSettings();
    }

    public void saveTemperatureUnit(@WeatherSettings.TemperatureSettings int temperatureUnit) {
        settingsInteractor.saveTemperatureSettings(temperatureUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherSettings>() {

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError(e.getMessage());
                    }

                    @Override
                    public void onSuccess(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }

    public void savePressureUnit(@WeatherSettings.PressureSettings int pressureUnit) {
        settingsInteractor.savePressureSettings(pressureUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherSettings>() {

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError(e.getMessage());
                    }

                    @Override
                    public void onSuccess(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }

    public void saveWindSpeedUnit(@WeatherSettings.SpeedSettings int windSpeedUnit) {
        settingsInteractor.saveWindSpeedSettings(windSpeedUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherSettings>() {

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError(e.getMessage());
                    }

                    @Override
                    public void onSuccess(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }

    public void getWeatherSettings() {
        settingsInteractor.getWeatherSettings()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherSettings>() {

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError(e.getMessage());
                    }

                    @Override
                    public void onSuccess(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }
}