package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;


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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
        myStationViewHolder.onBind(myStations.get(position));
    }

    @Override
    public int getItemCount() {
        return myStations.size();
    }

    static class MyStationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyStationsListener myStationsListener;
        MainWeatherModel mainWeatherModel;

        private TextView tvName;
        private TextView tvTemperatureDay;
        private TextView tvTemperatureNight;

        private TextView tvWeather;


        public MyStationViewHolder(View itemView, MyStationsListener myStationsListener) {
            super(itemView);
            this.myStationsListener = myStationsListener;
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTemperatureDay = (TextView) itemView.findViewById(R.id.tvTemperatureDay);
            tvTemperatureNight = (TextView) itemView.findViewById(R.id.tvTemperatureNight);
            tvWeather = (TextView) itemView.findViewById(R.id.tvWeather);
        }

        public void onBind(MainWeatherModel mainWeatherModel) {
            this.mainWeatherModel = mainWeatherModel;
            tvName.setText(mainWeatherModel.getCity().getName());
            tvTemperatureDay.setText(Double.toString(mainWeatherModel.getList().get(0).getTemp().getDay()));
            tvTemperatureNight.setText(Double.toString(mainWeatherModel.getList().get(0).getTemp().getNight()));

            tvWeather.setText(mainWeatherModel.getList().get(0).getWeather().get(0).getDescription());
        }

        @Override
        public void onClick(View view) {

        }
    }
}