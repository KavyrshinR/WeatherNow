package ru.kavyrshin.weathernow.view;


import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {

    void showLoad();
    void hideLoad();
}