package ru.kavyrshin.weathernow.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.models.StationListElement;

public class ArroundStationsAdapter extends RecyclerView.Adapter<ArroundStationsAdapter.ArroundStationViewHolder> {

    public interface ArroundStationsListener {
        void onArroundStationClick(StationListElement stationListElement);
    }

    private ArroundStationsListener arroundStationsListener;

    private List<StationListElement> stationListElements = new ArrayList<>();

    public ArroundStationsAdapter(ArroundStationsListener arroundStationsListener) {
        this.arroundStationsListener = arroundStationsListener;
    }

    @Override
    public ArroundStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arround_station_item, parent, false);

        return new ArroundStationViewHolder(view, arroundStationsListener);
    }

    @Override
    public void onBindViewHolder(ArroundStationViewHolder holder, int position) {
        holder.onBind(stationListElements.get(position));
    }

    @Override
    public int getItemCount() {
        return stationListElements.size();
    }

    public void clearAll() {
        stationListElements.clear();
    }

    public void addAll(List<StationListElement> stations) {
        stationListElements.addAll(stations);
    }

    static class ArroundStationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ArroundStationsListener arroundStationsListener;
        StationListElement stationListElement;

        private TextView tvName;
        private TextView tvTemperature;


        public ArroundStationViewHolder(View itemView, ArroundStationsListener arroundStationsListener) {
            super(itemView);
            this.arroundStationsListener = arroundStationsListener;

            tvName = itemView.findViewById(R.id.tvName);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
        }

        public void onBind(StationListElement stationListElement) {
            this.stationListElement = stationListElement;
            tvName.setText(stationListElement.getName());
            tvTemperature.setText(itemView.getContext()
                    .getString(R.string.simple_temperature_format, stationListElement.getMain().getTemp()));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            arroundStationsListener.onArroundStationClick(stationListElement);
        }
    }
}