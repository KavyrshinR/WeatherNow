package ru.kavyrshin.weathernow.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.interactors.DetailedWeatherInteractor;
import ru.kavyrshin.weathernow.domain.models.ConcreteWeather;
import ru.kavyrshin.weathernow.presentation.view.DetailedWeatherView;

@InjectViewState
public class DetailedWeatherPresenter extends BasePresenter<DetailedWeatherView> {

    private int cityId;
    private int unixTime;

    private DetailedWeatherInteractor detailedWeatherInteractor;

    @Inject
    public DetailedWeatherPresenter(DetailedWeatherInteractor detailedWeatherInteractor,
                                    @Named("cityId") int cityId, @Named("unixTime") int unixTime) {
        this.detailedWeatherInteractor = detailedWeatherInteractor;
        this.cityId = cityId;
        this.unixTime = unixTime;
    }

    @Override
    protected void onFirstViewAttach() {
        if (cityId == -1) {
            getViewState().showError(R.string.error_unexpected_city);
        } else {
            getWeather();
        }
    }

    public void getWeather() {
        detailedWeatherInteractor.getWeatherByCityId(cityId, unixTime)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<ConcreteWeather>() {

                @Override
                public void onError(Throwable e) {
                    getViewState().showError(e.getMessage());
                }

                @Override
                public void onSuccess(ConcreteWeather concreteWeather) {
                    getViewState().showWeather(concreteWeather.getWeatherListElement(),
                            concreteWeather.getCityName());
                }
            });
    }

}
