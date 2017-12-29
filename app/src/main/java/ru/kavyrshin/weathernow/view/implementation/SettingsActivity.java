package ru.kavyrshin.weathernow.view.implementation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.presenter.SettingsPresenter;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import ru.kavyrshin.weathernow.view.SettingsView;


public class SettingsActivity extends BaseActivity implements SettingsView, View.OnClickListener {

    private LinearLayout temperatureButton;
    private TextView tvTemperatureValue;
    private LinearLayout pressureButton;
    private TextView tvPressureValue;
    private LinearLayout speedButton;
    private TextView tvSpeedValue;

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        temperatureButton = findViewById(R.id.temperatureButton);
        tvTemperatureValue = findViewById(R.id.tvTemperatureValue);
        pressureButton = findViewById(R.id.pressureButton);
        tvPressureValue = findViewById(R.id.tvPressureValue);
        speedButton = findViewById(R.id.speedButton);
        tvSpeedValue = findViewById(R.id.tvSpeedValue);

        temperatureButton.setOnClickListener(this);
        pressureButton.setOnClickListener(this);
        speedButton.setOnClickListener(this);
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showSettings(WeatherSettings weatherSettings) {

        int temperatureUnitSetting = weatherSettings.getTemperatureUnit();
        if (temperatureUnitSetting == WeatherSettings.CELSIUS_UNIT) {
            tvTemperatureValue.setText(R.string.celsius_symbol);
        } else if (temperatureUnitSetting == WeatherSettings.KELVIN_UNIT) {
            tvTemperatureValue.setText(R.string.kelvin_symbol);
        } else {
            tvTemperatureValue.setText(R.string.fahrenheit_symbol);
        }

        tvPressureValue.setText(weatherSettings.getPressureUnit() == WeatherSettings.MM_OF_MERCURY_UNIT ?
        R.string.mm_of_mercury_symbol : R.string.hPa_symbol);

        tvSpeedValue.setText(weatherSettings.getWindSpeedUnit() == WeatherSettings.M_PER_SEC_UNIT ?
        R.string.m_per_sec_symbol : R.string.mi_per_hour_symbol);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (view.getId()) {
            case R.id.temperatureButton : {
                builder.setItems(R.array.temperature_variants, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = WeatherSettings.CELSIUS_UNIT;
                        if (i == 1) {
                            result = WeatherSettings.KELVIN_UNIT;
                        } else if (i == 2) {
                            result = WeatherSettings.FAHRENHEIT_UNIT;
                        }
                        settingsPresenter.saveTemperatureUnit(result);
                    }
                });
                break;
            }

            case R.id.pressureButton : {
                builder.setItems(R.array.pressure_variants, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = WeatherSettings.MM_OF_MERCURY_UNIT;
                        if (i == 0) {
                            result = WeatherSettings.H_PA_UNIT;
                        }
                        settingsPresenter.savePressureUnit(result);
                    }
                });
                break;
            }

            case R.id.speedButton : {
                builder.setItems(R.array.wind_speed_variants, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = WeatherSettings.M_PER_SEC_UNIT;
                        if (i == 1) {
                            result = WeatherSettings.MI_PER_HOUR_UNIT;
                        }
                        settingsPresenter.saveWindSpeedUnit(result);
                    }
                });
                break;
            }
        }

        builder.setTitle("Выберите что нить");
        builder.show();
    }
}