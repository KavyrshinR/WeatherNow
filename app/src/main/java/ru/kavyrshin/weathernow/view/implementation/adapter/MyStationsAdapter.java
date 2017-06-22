package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MyStationsAdapter extends RecyclerView.Adapter<MyStationsAdapter.MyStationViewHolder> {

    

    @Override
    public MyStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyStationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class MyStationViewHolder extends RecyclerView.ViewHolder {



        public MyStationViewHolder(View itemView) {
            super(itemView);

        }
    }
}