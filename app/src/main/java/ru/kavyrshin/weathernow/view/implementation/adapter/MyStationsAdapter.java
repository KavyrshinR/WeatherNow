package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.entity.WeatherListElement;


public class MyStationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface MyStationsListener {
        void myStationClick();
    }

    private MyStationsListener myStationsListener;

    private ArrayList<MainWeatherModel> myStations = new ArrayList<>();

    public MyStationsAdapter(MyStationsListener myStationsListener) {
        this.myStationsListener = myStationsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_station_item, parent, false);
        MyStationViewHolder myStationViewHolder = new MyStationViewHolder(view, myStationsListener);
        return myStationViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { //TODO: Переписать что-ли
        MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
        myStationViewHolder.onBind(myStations.get(position / 7), position);
    }

    @Override
    public int getItemCount() {
        int result = 0;
        for (int i = 0; i < myStations.size(); i++) {
            result += myStations.get(i).getList().size();
        }
        return result;
    }

    public void setMyStations(List<MainWeatherModel> myStations) {
        this.myStations.addAll(myStations);
    }

    public void clearStations() {
        this.myStations.clear();
    }

    static class MyStationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyStationsListener myStationsListener;
        MainWeatherModel mainWeatherModel;

        private TextView tvDate;
        private TextView tvTemperatureDay;
        private TextView tvTemperatureNight;

        private ImageView ivWeather;


        public MyStationViewHolder(View itemView, MyStationsListener myStationsListener) {
            super(itemView);
            this.myStationsListener = myStationsListener;
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTemperatureDay = (TextView) itemView.findViewById(R.id.tvTemperatureDay);
            tvTemperatureNight = (TextView) itemView.findViewById(R.id.tvTemperatureNight);
            ivWeather = (ImageView) itemView.findViewById(R.id.ivWeather);
        }

        public void onBind(MainWeatherModel mainWeatherModel, int position) {
            this.mainWeatherModel = mainWeatherModel;

            WeatherListElement weatherListElement = mainWeatherModel.getList().get(position % 7);
            double dayTemp = weatherListElement.getTemp().getDay();
            double nightTemp = weatherListElement.getTemp().getNight();

            tvDate.setText(mainWeatherModel.getCity().getName());
            tvTemperatureDay.setText(dayTemp > 0 ? "+" + dayTemp : Double.toString(dayTemp));
            tvTemperatureNight.setText(nightTemp > 0 ? "+" + nightTemp : Double.toString(nightTemp));

            int resId = getIconResId(weatherListElement.getWeather().get(0).getId());
            if (resId > 0) {
                ivWeather.setImageResource(resId);
            }

        }

        @Override
        public void onClick(View view) {

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
}