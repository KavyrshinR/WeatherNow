package ru.kavyrshin.weathernow.view.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.WeatherListElement;
import ru.kavyrshin.weathernow.presenter.MyStationsPresenter;
import ru.kavyrshin.weathernow.view.MyStationsView;


public class MyStationsActivity extends BaseActivity implements View.OnClickListener, MyStationsView {

    public static final int REQUEST_STATION_ID_CODE = 1256;
    public static final String EXTEA_STATION_ID = "Extra StationID";

    @InjectPresenter
    MyStationsPresenter myStationsPresenter;

    private Toolbar toolbar;
    private TextView tvToolbar;

    private FloatingActionButton btnAddStation;

    private RecyclerView stationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbar = (TextView) toolbar.findViewById(R.id.tvOut);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle("Мои станции тип");

        btnAddStation = (FloatingActionButton) findViewById(R.id.btnAddStation);
        stationList = (RecyclerView) findViewById(R.id.stationList);

        btnAddStation.setOnClickListener(this);

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

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showMyStations(List<WeatherListElement> weatherListElements) {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddStation) {
            Intent intent = new Intent(this, AddStationActivity.class);
            startActivityForResult(intent, REQUEST_STATION_ID_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_STATION_ID_CODE) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra(MyStationsActivity.EXTEA_STATION_ID, -1);

                if (result != -1) {
                    Log.d("myLogs", "Result " + result);

                }
            }
        }
    }
}
