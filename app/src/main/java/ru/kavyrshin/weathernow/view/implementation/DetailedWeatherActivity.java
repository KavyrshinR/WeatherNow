package ru.kavyrshin.weathernow.view.implementation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.entity.WeatherListElement;
import ru.kavyrshin.weathernow.presenter.DetailedWeatherPresenter;
import ru.kavyrshin.weathernow.view.DetailedWeatherView;

public class DetailedWeatherActivity extends BaseActivity implements DetailedWeatherView {

    public static final String CITY_ID_EXTRA = "City Id Extra";
    public static final String UNIXTIME_EXTRA = "UnixTime Extra";

    private static SimpleDateFormat goodDateFormat = new SimpleDateFormat("dd.MM HH:mm");

    private int cityId;
    private int unixTimeWeather;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_weather_activity);

        imageWeather = (ImageView) findViewById(R.id.imageWeather);
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTempDay = (TextView) findViewById(R.id.tvTempDay);
        tvTempNight = (TextView) findViewById(R.id.tvTempNight);
        tvWindDirection = (TextView) findViewById(R.id.tvWindDirection);
        tvWindSpeed = (TextView) findViewById(R.id.tvWindSpeed);
        tvPressure = (TextView) findViewById(R.id.tvPressure);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);

        cityId = getIntent().getIntExtra(CITY_ID_EXTRA, -1);
        unixTimeWeather = getIntent().getIntExtra(UNIXTIME_EXTRA, -1);

        if (cityId != -1) {
            detailedWeatherPresenter.getWeatherByCityId(cityId);
        } else {
            showError("Unexpected city");
        }
    }

    @Override
    public void showWeather(MainWeatherModel weatherModel) {
        WeatherListElement weather = null;
        for (WeatherListElement listElement : weatherModel.getList()) {
            if (listElement.getDt() == unixTimeWeather) {
                weather = listElement;
                break;
            }
        }

        if (weather != null) {
            int resId = getIconResId(weather.getWeather().get(0).getId());
            imageWeather.setImageResource(resId);
            tvCityName.setText(weatherModel.getCity().getName());

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            goodDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis((weather.getLocalDt() * 1000L));
            String formattedDate = goodDateFormat.format(calendar.getTime());

            tvDate.setText(formattedDate);
            tvTempDay.setText(String.format(Locale.getDefault(), "%.2f", weather.getTemp().getDay()));
            tvTempNight.setText(String.format(Locale.getDefault(), "%.2f", weather.getTemp().getNight()));

            int wordWindDirection = windDirectionWordResId(weather.getDeg());
            tvWindDirection.setText(wordWindDirection);

            tvWindSpeed.setText(String.format(Locale.getDefault(),"%.2f", weather.getSpeed()));
            tvPressure.setText(String.format(Locale.getDefault(), "%.2f", weather.getPressure()));
            tvHumidity.setText(String.format(Locale.getDefault(), "%d%%", weather.getHumidity()));
        }
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
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
