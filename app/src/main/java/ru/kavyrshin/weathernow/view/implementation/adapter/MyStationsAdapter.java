package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;


public class MyStationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private StationWeatherAdapter.MyStationsListener myStationsListener;
    private ArrayList<MainWeatherModel> myStations = new ArrayList<>();

    public MyStationsAdapter(StationWeatherAdapter.MyStationsListener myStationsListener) {
        this.myStationsListener = myStationsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.station_list_item, parent, false);
        MyStationViewHolder myStationViewHolder = new MyStationViewHolder(view);
        return myStationViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
        myStationViewHolder.cityName.setText(myStations.get(position).getCity().getName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myStationViewHolder.itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        myStationViewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        myStationViewHolder.recyclerView.setAdapter(new StationWeatherAdapter(myStationsListener, myStations.get(position)));
    }

    @Override
    public int getItemCount() {
        return myStations.size();
    }

    public void setMyStations(List<MainWeatherModel> myStations) {
        this.myStations.addAll(myStations);
    }

    public void clearStations() {
        this.myStations.clear();
    }

    static class MyStationViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        RecyclerView recyclerView;

        public MyStationViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.cityName);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.weatherList);
        }

    }
}