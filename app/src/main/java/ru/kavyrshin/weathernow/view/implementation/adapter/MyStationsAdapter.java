package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.entity.MainWeatherModel;


public class MyStationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainWeatherModel> myStations = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyStationViewHolder myStationViewHolder = new MyStationViewHolder(new RecyclerView(parent.getContext()));
        return myStationViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
        myStationViewHolder.recyclerView.setAdapter(new MyStationsAdapterInner(null, myStations.get(position)));
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