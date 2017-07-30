package ru.kavyrshin.weathernow.presenter;

import com.arellomobile.mvp.InjectViewState;

import ru.kavyrshin.weathernow.model.DataManager;
import ru.kavyrshin.weathernow.view.MyStationsView;

@InjectViewState
public class MyStationsPresenter extends BasePresenter<MyStationsView> {

    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        dataManager.getFavouriteStations();
    }
}