package ru.kavyrshin.weathernow.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.interactors.SettingsInteractor;
import ru.kavyrshin.weathernow.presentation.view.SettingsView;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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
                .subscribe(new Subscriber<WeatherSettings>() {
                    @Override
                    public void onCompleted() {
                        Log.d("myLogs", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }

    public void savePressureUnit(@WeatherSettings.PressureSettings int pressureUnit) {
        settingsInteractor.savePressureSettings(pressureUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherSettings>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }

    public void saveWindSpeedUnit(@WeatherSettings.SpeedSettings int windSpeedUnit) {
        settingsInteractor.saveWindSpeedSettings(windSpeedUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherSettings>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }

    public void getWeatherSettings() {
        settingsInteractor.getWeatherSettings()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherSettings>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherSettings weatherSettings) {
                        getViewState().showSettings(weatherSettings);
                    }
                });
    }
}