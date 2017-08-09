package ru.kavyrshin.weathernow.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import ru.kavyrshin.weathernow.entity.CacheCity;
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

    public void loadFavouriteStations() {
        ArrayList<CacheCity> favouriteCitys = new ArrayList<>(dataManager.getFavouriteStations());

        int[] cityIds = new int[favouriteCitys.size()];

        for (int i = 0; i < favouriteCitys.size(); i++) {
            CacheCity item = favouriteCitys.get(i);
            cityIds[i] = item.getId();
        }

        dataManager.getWeather(cityIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MainWeatherModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MainWeatherModel> mainWeatherModels) {
                        getViewState().showMyStations(mainWeatherModels);
                    }
                });
    }
}