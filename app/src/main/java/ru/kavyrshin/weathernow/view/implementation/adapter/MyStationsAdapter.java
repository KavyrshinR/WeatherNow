package ru.kavyrshin.weathernow.view.implementation.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;


public class MyStationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int STATION_LIST_ITEM = R.layout.station_list_item;
    private final int SPACE_LIST_ITEM = R.layout.space_list_item;

    private StationWeatherAdapter.MyStationsListener myStationsListener;
    private ArrayList<MainWeatherModel> myStations = new ArrayList<>();

    public MyStationsAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == STATION_LIST_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.station_list_item, parent, false);
            MyStationViewHolder myStationViewHolder = new MyStationViewHolder(view);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myStationViewHolder.itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            myStationViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
            SnapHelper pagerSnapHelper = new MySnapHelper();
            pagerSnapHelper.attachToRecyclerView(myStationViewHolder.recyclerView);

            return myStationViewHolder;
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.space_list_item, parent, false);
            SpaceViewHolder spaceViewHolder = new SpaceViewHolder(view);
            return spaceViewHolder;
        }
    }

    public void setMyStationsListener(StationWeatherAdapter.MyStationsListener myStationsListener) {
        this.myStationsListener = myStationsListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);

        if (itemType == STATION_LIST_ITEM) {
            MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
            myStationViewHolder.cityName.setText(myStations.get(position).getCity().getName());

            myStationViewHolder.recyclerView.setAdapter(new StationWeatherAdapter(myStationsListener, myStations.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return myStations.size()
                + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < myStations.size()) {
            return STATION_LIST_ITEM;
        }

        return SPACE_LIST_ITEM;
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

    static class SpaceViewHolder extends RecyclerView.ViewHolder {

        public SpaceViewHolder(View itemView) {
            super(itemView);

        }

    }
}