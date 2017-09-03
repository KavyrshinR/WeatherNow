package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.entity.MainWeatherModel;


public class MyStationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private StationWeatherAdapter.MyStationsListener myStationsListener;
    private ArrayList<MainWeatherModel> myStations = new ArrayList<>();

    public MyStationsAdapter(StationWeatherAdapter.MyStationsListener myStationsListener) {
        this.myStationsListener = myStationsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView recyclerView = new RecyclerView(parent.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyStationViewHolder myStationViewHolder = new MyStationViewHolder(recyclerView);
        return myStationViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
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

        RecyclerView recyclerView;

        public MyStationViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
        }

    }
}