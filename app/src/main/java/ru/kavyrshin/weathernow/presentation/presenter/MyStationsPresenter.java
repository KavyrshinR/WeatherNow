package ru.kavyrshin.weathernow.presentation.presenter;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.interactors.MyStationsInteractor;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.presentation.view.MyStationsView;


@InjectViewState
public class MyStationsPresenter extends BasePresenter<MyStationsView> {

    private MyStationsInteractor myStationsInteractor;

    @Inject
    public MyStationsPresenter(MyStationsInteractor myStationsInteractor) {
        this.myStationsInteractor = myStationsInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadFavouriteStations();
    }

    public void deleteFavouriteStation(int cityId) {
        unsubscribeOnDestroy(
            myStationsInteractor
                    .deleteFavouriteStation(cityId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Boolean>() {

                        @Override
                        public void onError(Throwable e) {
                            getViewState().showError(e.getMessage());
                        }

                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            if (aBoolean) {
                                loadFavouriteStations();
                            } else {
                                onError(new RuntimeException("DB error"));
                            }
                        }
                    })
        );
    }

    public void loadFavouriteStations() {

        unsubscribeOnDestroy(
                myStationsInteractor.getAllWeather()
                        .observeOn(AndroidSchedulers.mainThread(), true)
                        .subscribeWith(new DisposableObserver<Pair<DataSource, List<MainWeatherModel>>>() {

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                getViewState().hideLoad();
                                getViewState().showError(e.getMessage());
                            }

                            @Override
                            public void onNext(Pair<DataSource, List<MainWeatherModel>> mainWeatherModels) {
                                if (mainWeatherModels.second.isEmpty()) {
                                    getViewState().hideLoad();
                                    getViewState().showMyStations(new ArrayList<MainWeatherModel>());
                                    getViewState().showError(R.string.error_empty_favourite_stations);
                                } else {
                                    getViewState().showMyStations(mainWeatherModels.second);

                                    if (mainWeatherModels.first.getSource() == DataSource.INTERNET_DATA_SOURCE) {
                                        getViewState().hideLoad();
                                    }
                                }

                            }
                        })
        );
    }

    public void addStationsClick() {
        getViewState().goToAddStation();
    }

    public void detailClick(int cityId, int unixTime) {
        getViewState().goToDetail(cityId, unixTime);
    }
}