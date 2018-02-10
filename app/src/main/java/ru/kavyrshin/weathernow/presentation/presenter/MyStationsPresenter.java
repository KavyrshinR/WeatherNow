package ru.kavyrshin.weathernow.presentation.presenter;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.MyApplication;
import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.entity.CacheCity;
import ru.kavyrshin.weathernow.entity.DataSource;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.data.DataManager;
import ru.kavyrshin.weathernow.presentation.view.MyStationsView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class MyStationsPresenter extends BasePresenter<MyStationsView> {

    private DataManager dataManager = DataManager.getInstance();

    @Inject
    public MyStationsPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadFavouriteStations();
    }

    public void deleteFavouriteStation(int cityId) {
        dataManager.deleteFavouriteStation(cityId);
        dataManager.deleteWeatherByStationId(cityId);
        loadFavouriteStations();
    }

    public void loadFavouriteStations() {
        ArrayList<CacheCity> favouriteCitys = new ArrayList<>(dataManager.getFavouriteStations());

        if (favouriteCitys.isEmpty()) {
            getViewState().hideLoad();
            getViewState().showMyStations(new ArrayList<MainWeatherModel>());
            getViewState().showError(R.string.error_empty_favourite_stations);
            return;
        }

        int[] cityIds = new int[favouriteCitys.size()];

        for (int i = 0; i < favouriteCitys.size(); i++) {
            CacheCity item = favouriteCitys.get(i);
            cityIds[i] = item.getId();
        }

        if (!MyApplication.isNetworkConnected()) {
            Pair<DataSource, List<MainWeatherModel>> cachedWeather = dataManager.getCachedWeather();
            getViewState().showMyStations(cachedWeather.second);
            getViewState().hideLoad();
        } else {

            unsubscribeOnDestroy(
                    dataManager.getWeather(cityIds)
                            .startWith(dataManager.getCachedWeather())
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
                                    getViewState().showMyStations(mainWeatherModels.second);
                                    if (mainWeatherModels.first.getSource() == DataSource.INTERNET_DATA_SOURCE) {
                                        getViewState().hideLoad();
                                    }
                                }
                            })
            );
        }
    }

    public void addStationsClick() {
        getViewState().goToAddStation();
    }

    public void detailClick(int cityId, int unixTime) {
        getViewState().goToDetail(cityId, unixTime);
    }
}