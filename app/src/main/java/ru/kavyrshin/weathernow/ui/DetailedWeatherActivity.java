package ru.kavyrshin.weathernow.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.models.WeatherListElement;
import ru.kavyrshin.weathernow.presentation.presenter.DetailedWeatherPresenter;
import ru.kavyrshin.weathernow.presentation.view.DetailedWeatherView;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;

public class DetailedWeatherActivity extends BaseActivity implements DetailedWeatherView {

    public static final String CITY_ID_EXTRA = "City Id Extra";
    public static final String UNIXTIME_EXTRA = "UnixTime Extra";

    private static SimpleDateFormat goodDateFormat = new SimpleDateFormat("dd.MM HH:mm");

    private Toolbar toolbar;
    private TextView tvToolbar;

    private ImageView imageWeather;
    private TextView tvCityName;
    private TextView tvDate;
    private TextView tvTempDay;
    private TextView tvTempNight;
    private TextView tvWindDirection;
    private TextView tvWindSpeed;
    private TextView tvPressure;
    private TextView tvHumidity;


    @InjectPresenter
    DetailedWeatherPresenter detailedWeatherPresenter;

    @ProvidePresenter
    DetailedWeatherPresenter providePresenter() {
        return myApplication().getApplicationComponent().detailedWeatherComponent()
                .cityId(getIntent().getIntExtra(CITY_ID_EXTRA, -1))
                .unixTime(getIntent().getIntExtra(UNIXTIME_EXTRA, -1))
                .build().presenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_weather_activity);

        toolbar = findViewById(R.id.toolbar);
        tvToolbar = toolbar.findViewById(R.id.tvOut);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle("Detailed Weather");

        imageWeather = findViewById(R.id.imageWeather);
        tvCityName = findViewById(R.id.tvCityName);
        tvDate = findViewById(R.id.tvDate);
        tvTempDay = findViewById(R.id.tvTempDay);
        tvTempNight = findViewById(R.id.tvTempNight);
        tvWindDirection = findViewById(R.id.tvWindDirection);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
    }

    @Override
    public void showWeather(WeatherListElement weather, String cityName, WeatherSettings weatherSettings) {

        if (weather != null) {
            tvToolbar.setText(cityName);

            int resIdIcon = getIconResId(weather.getWeather().get(0).getId());
            imageWeather.setImageResource(resIdIcon);
            tvCityName.setText(cityName);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            goodDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis((weather.getLocalDt() * 1000L));
            String formattedDate = goodDateFormat.format(calendar.getTime());

            tvDate.setText(formattedDate);

            int resIdTemperature = R.string.temperature_celsius;

            switch (weatherSettings.getTemperatureUnit()) {
                case Utils.KELVIN_UNIT : {
                    resIdTemperature = R.string.temperature_kelvin;
                    break;
                }

                case Utils.FAHRENHEIT_UNIT : {
                    resIdTemperature = R.string.temperature_fahrenheit;
                    break;
                }
            }

            tvTempDay.setText(getString(resIdTemperature, weather.getTemp().getDay()));
            tvTempNight.setText(getString(resIdTemperature, weather.getTemp().getNight()));

            int wordWindDirection = windDirectionWordResId(weather.getDeg());
            tvWindDirection.setText(wordWindDirection);

            int resIdWindSpeed = R.string.wind_m_per_sec;

            if (weatherSettings.getWindSpeedUnit() == Utils.MI_PER_HOUR_UNIT) {
                resIdWindSpeed = R.string.wind_mi_per_hour;
            }
            tvWindSpeed.setText(getString(resIdWindSpeed, weather.getSpeed()));

            int resIdPressure = R.string.pressure_mmHg;

            if (weatherSettings.getPressureUnit() == Utils.H_PA_UNIT) {
                resIdPressure = R.string.pressure_hPa;
            }

            tvPressure.setText(getString(resIdPressure, weather.getPressure()));
            tvHumidity.setText(getString(R.string.humidity, weather.getHumidity()));
        }
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int textRes) {
        Toast.makeText(this, textRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    public int windDirectionWordResId(int degrees) {
        int result;

        if (degrees <= 22) {
            result = R.string.north_char;
        } else if (degrees <= 67) {
            result = R.string.north_east_char;
        } else if (degrees <= 112) {
            result = R.string.east_char;
        } else if (degrees <= 157) {
            result = R.string.south_east_char;
        } else if (degrees <= 202) {
            result = R.string.south_char;
        } else if (degrees <= 247) {
            result = R.string.south_west_char;
        } else if (degrees <= 292) {
            result = R.string.west_char;
        } else if (degrees <= 337) {
            result = R.string.north_west_char;
        } else {
            result = R.string.north_char;
        }

        return result;
    }

    public int getIconResId(int weatherId) { //https://openweathermap.org/weather-conditions
        int resId = 0;
        switch (weatherId) {
            case 200 :
            case 201 :
            case 202 :
            case 210 :
            case 211 :
            case 212 :
            case 221 :
            case 230 :
            case 231 :
            case 232 : {
                resId = R.drawable.ic_storm;
                break;
            }

            case 300 :
            case 301 :
            case 302 :
            case 310 :
            case 311 :
            case 312 :
            case 313 :
            case 314 :
            case 321 : {
                resId = R.drawable.ic_shower_rain;
                break;
            }

            case 500 :
            case 501 : {
                resId = R.drawable.ic_rain;
                break;
            }
            case 502 :
            case 503 :
            case 504 : {
                resId = R.drawable.ic_heavy_rain;
                break;
            }
            case 511 : {
                resId = R.drawable.ic_snow_cloud;
                break;
            }
            case 520 :
            case 521 :
            case 522 :
            case 531 : {
                resId = R.drawable.ic_shower_rain;
                break;
            }

            case 600 :
            case 601 :
            case 602 :
            case 611 :
            case 612 : {
                resId = R.drawable.ic_snow;
                break;
            }
            case 615 :
            case 616 : {
                resId = R.drawable.ic_snow;
                break;
            }
            case 620 :
            case 621 :
            case 622 : {
                resId = R.drawable.ic_snow;
                break;
            }

            case 701 :
            case 711 :
            case 721 :
            case 731 :
            case 741 :
            case 751 :
            case 761 :
            case 762 :
            case 771 :
            case 781 : {
                resId = R.drawable.ic_cloud;
                break;
            }

            case 800 : {
                resId = R.drawable.ic_sun;
                break;
            }
            case 801 : {
                resId = R.drawable.ic_sun_cloud;
                break;
            }
            case 802 :
            case 803 :
            case 804 : {
                resId = R.drawable.ic_cloud;
                break;
            }

            case 900 : {
                resId = R.drawable.ic_tornado;
                break;
            }

            case 901 :
            case 902 :
            case 905 : {
                resId = R.drawable.ic_wind;
                break;
            }
        }
        return resId;
    }
}
