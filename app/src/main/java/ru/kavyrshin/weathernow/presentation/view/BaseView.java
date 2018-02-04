package ru.kavyrshin.weathernow.presentation.view;


import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {
    void showError(String errorMessage);
    void showLoad();
    void hideLoad();
}