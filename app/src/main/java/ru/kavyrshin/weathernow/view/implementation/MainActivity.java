package ru.kavyrshin.weathernow.view.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ru.kavyrshin.weathernow.R;


public class MainActivity extends BaseActivity implements View.OnClickListener {

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

        btnAddStation = (FloatingActionButton) findViewById(R.id.btnAddStation);
        stationList = (RecyclerView) findViewById(R.id.stationList);

        btnAddStation.setOnClickListener(this);

        setTitle("Хуй");
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            tvToolbar.setText(title);
        }
        super.setTitle(title);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddStation) {
            Intent intent = new Intent(this, AddStationActivity.class);
            startActivity(intent);
        }
    }
}
