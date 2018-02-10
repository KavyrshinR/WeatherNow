package ru.kavyrshin.weathernow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.presentation.presenter.AddStationPresenter;
import ru.kavyrshin.weathernow.presentation.view.AddStationView;
import ru.kavyrshin.weathernow.ui.adapter.ArroundStationsAdapter;


public class AddStationActivity extends BaseActivity implements AddStationView, ArroundStationsAdapter.ArroundStationsListener {

    @InjectPresenter
    AddStationPresenter addStationPresenter;

    private PlaceAutocompleteFragment placeAutocompleteFragment;

    private RecyclerView arroundStations;
    private ArroundStationsAdapter arroundStationsAdapter;

    private ProgressBar progressBar;


    @ProvidePresenter
    AddStationPresenter providePresenter() {
        return myApplication().getApplicationComponent().addStationComponent().build().presenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_station_activity);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
                addStationPresenter.getArroundStations(place.getName().toString(),
                        place.getLatLng().latitude, place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                showError(status.getStatusMessage());
            }
        });

        arroundStations = findViewById(R.id.arroundStations);
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
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showArroundStations(List<StationListElement> listArroundStations) {
        arroundStationsAdapter.clearAll();
        arroundStationsAdapter.addAll(listArroundStations);
        arroundStationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoad() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onArroundStationClick(StationListElement stationListElement) {
        addStationPresenter.addStation(stationListElement.getId());
    }

    @Override
    public void stationAdded() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}