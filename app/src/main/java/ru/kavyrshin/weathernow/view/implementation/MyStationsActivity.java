package ru.kavyrshin.weathernow.view.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.presenter.MyStationsPresenter;
import ru.kavyrshin.weathernow.view.MyStationsView;
import ru.kavyrshin.weathernow.view.implementation.adapter.MyStationsAdapter;
import ru.kavyrshin.weathernow.view.implementation.adapter.StationWeatherAdapter;


public class MyStationsActivity extends BaseActivity implements View.OnClickListener, MyStationsView,
        StationWeatherAdapter.MyStationsListener {

    public static final int REQUEST_STATION_ID_CODE = 1256;
    public static final String EXTEA_STATION_ID = "Extra StationID";

    @InjectPresenter
    MyStationsPresenter myStationsPresenter;

    private Toolbar toolbar;
    private TextView tvToolbar;

    private FloatingActionButton btnAddStation;

    private RecyclerView stationList;
    private MyStationsAdapter myStationsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        tvToolbar = toolbar.findViewById(R.id.tvOut);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle(getString(R.string.my_stations_actionbar_title));

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        btnAddStation = findViewById(R.id.btnAddStation);
        stationList = findViewById(R.id.stationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stationList.setLayoutManager(linearLayoutManager);
        myStationsAdapter = new MyStationsAdapter();
        stationList.setAdapter(myStationsAdapter);

        btnAddStation.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myStationsPresenter.loadFavouriteStations();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_stations_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myStationsAdapter.setMyStationsListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myStationsAdapter.setMyStationsListener(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings : {

                return true;
            }

            case R.id.menu_about : {
                Intent intent = new Intent(MyStationsActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void myStationClick(int cityId, int unixTime) {
        myStationsPresenter.detailClick(cityId, unixTime);
        Toast.makeText(this, "cityId " + cityId + " time " + unixTime, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myStationLongClick(int cityId, int unixTime) {
        myStationsPresenter.deleteFavouriteStation(cityId);
        Toast.makeText(this, "Удалил" + cityId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            tvToolbar.setText(title);
        }
        super.setTitle(title);
    }

    @Override
    public void showError(String errorMessage) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showMyStations(List<MainWeatherModel> weatherListElements) {
        myStationsAdapter.clearStations();
        myStationsAdapter.setMyStations(weatherListElements);
        myStationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoad() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddStation) {
            myStationsPresenter.addStationsClick();
        }
    }

    @Override
    public void goToAddStation() {
        Intent intent = new Intent(this, AddStationActivity.class);
        startActivityForResult(intent, REQUEST_STATION_ID_CODE);
    }

    @Override
    public void goToDetail(int cityId, int unixTime) {
        Intent intent = new Intent(this, DetailedWeatherActivity.class);
        intent.putExtra(DetailedWeatherActivity.CITY_ID_EXTRA, cityId);
        intent.putExtra(DetailedWeatherActivity.UNIXTIME_EXTRA, unixTime);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_STATION_ID_CODE) {
            if (resultCode == RESULT_OK) {
                myStationsPresenter.loadFavouriteStations();
            }
        }
    }
}
