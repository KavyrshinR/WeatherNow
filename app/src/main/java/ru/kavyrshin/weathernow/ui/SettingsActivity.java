package ru.kavyrshin.weathernow.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.presentation.presenter.SettingsPresenter;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import ru.kavyrshin.weathernow.presentation.view.SettingsView;


public class SettingsActivity extends BaseActivity implements SettingsView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvToolbar;

    private LinearLayout temperatureButton;
    private TextView tvTemperatureValue;
    private LinearLayout pressureButton;
    private TextView tvPressureValue;
    private LinearLayout speedButton;
    private TextView tvSpeedValue;

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    @ProvidePresenter
    SettingsPresenter providePresenter() {
        return myApplication().getApplicationComponent().settingsScreenComponent().build().presenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        toolbar = findViewById(R.id.toolbar);
        tvToolbar = toolbar.findViewById(R.id.tvOut);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle(getString(R.string.settings_title));

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
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            tvToolbar.setText(title);
        }
        super.setTitle(title);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSettings(WeatherSettings weatherSettings) {

        int temperatureUnitSetting = weatherSettings.getTemperatureUnit();
        if (temperatureUnitSetting == Utils.CELSIUS_UNIT) {
            tvTemperatureValue.setText(R.string.celsius_symbol);
        } else if (temperatureUnitSetting == Utils.KELVIN_UNIT) {
            tvTemperatureValue.setText(R.string.kelvin_symbol);
        } else {
            tvTemperatureValue.setText(R.string.fahrenheit_symbol);
        }

        tvPressureValue.setText(weatherSettings.getPressureUnit() == Utils.MM_OF_MERCURY_UNIT ?
        R.string.mm_of_mercury_symbol : R.string.hPa_symbol);

        tvSpeedValue.setText(weatherSettings.getWindSpeedUnit() == Utils.M_PER_SEC_UNIT ?
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
                        int result = Utils.CELSIUS_UNIT;
                        if (i == 1) {
                            result = Utils.KELVIN_UNIT;
                        } else if (i == 2) {
                            result = Utils.FAHRENHEIT_UNIT;
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
                        int result = Utils.MM_OF_MERCURY_UNIT;
                        if (i == 0) {
                            result = Utils.H_PA_UNIT;
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
                        int result = Utils.M_PER_SEC_UNIT;
                        if (i == 1) {
                            result = Utils.MI_PER_HOUR_UNIT;
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