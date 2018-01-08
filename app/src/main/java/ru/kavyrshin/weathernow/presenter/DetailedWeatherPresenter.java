package ru.kavyrshin.weathernow.presenter;

import com.arellomobile.mvp.InjectViewState;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.model.DataManager;
import ru.kavyrshin.weathernow.view.DetailedWeatherView;

@InjectViewState
public class DetailedWeatherPresenter extends BasePresenter<DetailedWeatherView> {

    private DataManager dataManager = DataManager.getInstance();

    public void getWeatherByCityId(int cityId) {
        if (cityId == -1) {
            getViewState().showError(R.string.error_unexpected_city);
            return;
        }
        MainWeatherModel weatherModel = dataManager.getCachedWeatherById(cityId);
        getViewState().showWeather(weatherModel, dataManager.getWeatherSettings());
    }

}
