package ru.kavyrshin.weathernow.presentation.presenter;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.domain.interactors.MyStationsInteractor;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.presentation.view.MyStationsView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class MyStationsPresenter extends BasePresenter<MyStationsView> {

//    private DataManager dataManager = DataManager.getInstance();

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
        myStationsInteractor.deleteFavouriteStation(cityId);
        loadFavouriteStations();
    }

    public void loadFavouriteStations() {
//        if (favouriteCities.isEmpty()) {
//            getViewState().hideLoad();
//            getViewState().showMyStations(new ArrayList<MainWeatherModel>());
//            getViewState().showError(R.string.error_empty_favourite_stations);
//            return;
//        }

//        if (!MyApplication.isNetworkConnected()) {
//            Pair<DataSource, List<MainWeatherModel>> cachedWeather = myStationsInteractor.getAllCachedWeather();
//            getViewState().showMyStations(cachedWeather.second);
//            getViewState().hideLoad();
//        } else {

        unsubscribeOnDestroy(
                myStationsInteractor.getAllWeather()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Pair<DataSource, List<MainWeatherModel>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                getViewState().hideLoad();
                                getViewState().showError(e.getMessage());
                            }

                            @Override
                            public void onNext(Pair<DataSource, List<MainWeatherModel>> mainWeatherModels) {
                                if (mainWeatherModels == null) {
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
//        }
    }

    public void addStationsClick() {
        getViewState().goToAddStation();
    }

    public void detailClick(int cityId, int unixTime) {
        getViewState().goToDetail(cityId, unixTime);
    }
}