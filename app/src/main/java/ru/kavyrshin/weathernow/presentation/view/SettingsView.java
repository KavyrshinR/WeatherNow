package ru.kavyrshin.weathernow.presentation.view;

import ru.kavyrshin.weathernow.util.WeatherSettings;

public interface SettingsView extends BaseView {

    void showSettings(WeatherSettings weatherSettings);
}