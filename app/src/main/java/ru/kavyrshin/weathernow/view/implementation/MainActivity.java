package ru.kavyrshin.weathernow.view.implementation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import ru.kavyrshin.weathernow.R;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnAddStation;

    private RecyclerView stationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddStation = (FloatingActionButton) findViewById(R.id.btnAddStation);
        stationList = (RecyclerView) findViewById(R.id.stationList);


    }
}
