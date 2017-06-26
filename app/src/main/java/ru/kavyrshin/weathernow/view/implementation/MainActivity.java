package ru.kavyrshin.weathernow.view.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.kavyrshin.weathernow.R;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton btnAddStation;

    private RecyclerView stationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddStation = (FloatingActionButton) findViewById(R.id.btnAddStation);
        stationList = (RecyclerView) findViewById(R.id.stationList);

        btnAddStation.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddStation) {
            Intent intent = new Intent(this, AddStationActivity.class);
            startActivity(intent);
        }
    }
}
