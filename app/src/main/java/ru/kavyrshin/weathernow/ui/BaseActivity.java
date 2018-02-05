package ru.kavyrshin.weathernow.ui;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.kavyrshin.weathernow.MyApplication;


public class BaseActivity extends MvpAppCompatActivity {

    private MyApplication application;

    public MyApplication myApplication() {
        if (application == null) {
            application = (MyApplication) getApplication();
        }
        return application;
    }
}