package ru.kavyrshin.weathernow.model;


import java.util.List;

import ru.kavyrshin.weathernow.BuildConfig;
import ru.kavyrshin.weathernow.MyApplication;
import ru.kavyrshin.weathernow.entity.MainStationModel;
import ru.kavyrshin.weathernow.entity.StationListElement;
import ru.kavyrshin.weathernow.model.api.ApiModule;
import ru.kavyrshin.weathernow.model.api.ApiWeather;
import ru.kavyrshin.weathernow.model.exception.CustomException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataManager {

    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    private DataManager() {
    }

    private ApiWeather apiWeather = ApiModule.getInstance();

    public Observable<List<StationListElement>> getStationArround(double latitude, double longitude) {

        Observable<MainStationModel> mainStationObservable = apiWeather.getStationArround(latitude,
                longitude, ApiModule.countStationArround, ApiModule.units, BuildConfig.API_KEY);

        if (!MyApplication.isNetworkConnected()) {
            return Observable.error(new CustomException(CustomException.NETWORK_EXCEPTION, "Нет подключения"));
        } else {
            return mainStationObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<MainStationModel, Observable<List<StationListElement>>>() {
                        @Override
                        public Observable<List<StationListElement>> call(MainStationModel mainStationModel) {
                            if ((mainStationModel != null) && (!mainStationModel.getList().isEmpty())) {
                                return Observable.just(mainStationModel.getList());
                            } else {
                                RuntimeException runtimeException = new RuntimeException();
                                return Observable.error(new CustomException(CustomException.SERVER_EXCEPTION, "Ошибка сервера"));
                            }
                        }
                    });
        }
    }
}