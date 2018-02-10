package ru.kavyrshin.weathernow.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;
import javax.inject.Named;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.models.WeatherListElement;
import ru.kavyrshin.weathernow.data.DataManager;
import ru.kavyrshin.weathernow.presentation.view.DetailedWeatherView;

@InjectViewState
public class DetailedWeatherPresenter extends BasePresenter<DetailedWeatherView> {

    private int cityId;
    private int unixTime;

    private DataManager dataManager = DataManager.getInstance();

    @Inject
    public DetailedWeatherPresenter(@Named("cityId") int cityId, @Named("unixTime") int unixTime) {
        this.cityId = cityId;
        this.unixTime = unixTime;
    }

    @Override
    protected void onFirstViewAttach() {
        getWeather();
    }

    public void getWeather() {
        if (cityId == -1) {
            getViewState().showError(R.string.error_unexpected_city);
            return;
        }
        MainWeatherModel mainWeatherModel = dataManager.getCachedWeatherById(cityId);

        WeatherListElement weather = null;
        for (WeatherListElement listElement : mainWeatherModel.getList()) {
            if (listElement.getDt() == unixTime) {
                weather = listElement;
                break;
            }
        }

        getViewState().showWeather(weather, mainWeatherModel.getCity().getName(), dataManager.getWeatherSettings());
    }

}
