package ru.kavyrshin.weathernow.view;

import ru.kavyrshin.weathernow.util.WeatherSettings;

public interface SettingsView extends BaseView {

    void showSettings(WeatherSettings weatherSettings);
}