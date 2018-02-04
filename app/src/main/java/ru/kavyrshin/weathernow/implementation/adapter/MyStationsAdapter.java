package ru.kavyrshin.weathernow.implementation.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;


public class MyStationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int STATION_LIST_ITEM = R.layout.station_list_item;
    private final int SPACE_LIST_ITEM = R.layout.space_list_item;

    private StationWeatherAdapter.MyStationsListener myStationsListener;
    private ArrayList<MainWeatherModel> myStations = new ArrayList<>();

    private HashMap<Integer, StationWeatherAdapter> adapters = new HashMap<>();

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemType = getItemViewType(position);

        if (itemType == STATION_LIST_ITEM) {
            final MyStationViewHolder myStationViewHolder = (MyStationViewHolder) holder;
            myStationViewHolder.cityName.setText(myStations.get(position).getCity().getName());
            myStationViewHolder.menuStation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myStationsListener != null) {
                        PopupMenu popupMenu = new PopupMenu(myStationViewHolder.itemView.getContext(),
                                myStationViewHolder.menuStation);
                        popupMenu.getMenuInflater().inflate(R.menu.station_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (myStationsListener != null) {
                                    myStationsListener.menuStationClick(myStations.get(position).getCityId(),
                                            item.getItemId());
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                }
            });

            if (adapters.get(myStations.get(position).getCityId()) == null) {
                adapters.put(myStations.get(position).getCityId(), new StationWeatherAdapter(myStationsListener, myStations.get(position)));
            }

            myStationViewHolder.recyclerView.setAdapter(adapters.get(myStations.get(position).getCityId()));
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

    public void clearAdapter() {
        this.adapters.clear();
    }

    static class MyStationViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        RecyclerView recyclerView;
        ImageButton menuStation;

        public MyStationViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            recyclerView = itemView.findViewById(R.id.weatherList);
            menuStation = itemView.findViewById(R.id.menu_station);
        }

    }

    static class SpaceViewHolder extends RecyclerView.ViewHolder {

        public SpaceViewHolder(View itemView) {
            super(itemView);

        }

    }
}