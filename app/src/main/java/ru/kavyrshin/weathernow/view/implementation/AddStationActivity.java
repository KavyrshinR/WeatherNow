package ru.kavyrshin.weathernow.view.implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.StationListElement;
import ru.kavyrshin.weathernow.presenter.AddStationPresenter;
import ru.kavyrshin.weathernow.view.AddStationView;
import ru.kavyrshin.weathernow.view.implementation.adapter.ArroundStationsAdapter;


public class AddStationActivity extends BaseActivity implements AddStationView, ArroundStationsAdapter.ArroundStationsListener {

    @InjectPresenter
    AddStationPresenter addStationPresenter;

    private PlaceAutocompleteFragment placeAutocompleteFragment;

    private RecyclerView arroundStations;
    private ArroundStationsAdapter arroundStationsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_station_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter onlyCityFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        placeAutocompleteFragment.setFilter(onlyCityFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                addStationPresenter.getArroundStations(place.getLatLng().latitude, place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                showError(status.getStatusMessage());
            }
        });

        arroundStations = (RecyclerView) findViewById(R.id.arroundStations);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        arroundStations.setLayoutManager(linearLayoutManager);
        arroundStationsAdapter = new ArroundStationsAdapter(this);
        arroundStations.setAdapter(arroundStationsAdapter);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showArroundStations(List<StationListElement> listArroundStations) {
        arroundStationsAdapter.clearAll();
        arroundStationsAdapter.addAll(listArroundStations);
        arroundStationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoad() {

    }


    @Override
    public void onArroundStationClick(StationListElement stationListElement) {
        Toast.makeText(this, stationListElement.getName(), Toast.LENGTH_SHORT).show();
    }
}