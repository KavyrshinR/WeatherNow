package ru.kavyrshin.weathernow.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.kavyrshin.weathernow.entity.MainWeatherModel;

public interface MyStationsView extends BaseView {

    void showMyStations(List<MainWeatherModel> weatherListElements);

    void showError(int textRes);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void goToAddStation();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void goToDetail(int cityId, int unixTime);
}