package ru.kavyrshin.weathernow.presenter;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.entity.CacheCity;
import ru.kavyrshin.weathernow.entity.DataSource;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.model.DataManager;
import ru.kavyrshin.weathernow.view.MyStationsView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class MyStationsPresenter extends BasePresenter<MyStationsView> {

    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadFavouriteStations();
    }

    public void deleteFavouriteStation(int cityId) {
        dataManager.deleteFavouriteStation(cityId);
    }

    public void loadFavouriteStations() {
        ArrayList<CacheCity> favouriteCitys = new ArrayList<>(dataManager.getFavouriteStations());

        int[] cityIds = new int[favouriteCitys.size()];

        for (int i = 0; i < favouriteCitys.size(); i++) {
            CacheCity item = favouriteCitys.get(i);
            cityIds[i] = item.getId();
        }

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