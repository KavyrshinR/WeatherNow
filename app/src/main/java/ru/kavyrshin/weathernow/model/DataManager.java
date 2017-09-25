package ru.kavyrshin.weathernow.model;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.kavyrshin.weathernow.BuildConfig;
import ru.kavyrshin.weathernow.MyApplication;
import ru.kavyrshin.weathernow.entity.CacheCity;
import ru.kavyrshin.weathernow.entity.MainStationModel;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.entity.StationListElement;
import ru.kavyrshin.weathernow.entity.TimeZone;
import ru.kavyrshin.weathernow.entity.WeatherListElement;
import ru.kavyrshin.weathernow.model.api.ApiModule;
import ru.kavyrshin.weathernow.model.api.ApiTimeZone;
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

    private ApiWeather apiWeather = ApiModule.getInstanceWeather();
    private ApiTimeZone apiTimeZone = ApiModule.getInstanceTimeZone();

    private Realm realm = Realm.getDefaultInstance();

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
                                return Observable.error(new CustomException(CustomException.SERVER_EXCEPTION, "Ошибка сервера"));
                            }
                        }
                    });
        }
    }

    public void saveStation(CacheCity city) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(city);
        realm.commitTransaction();
    }

    public List<CacheCity> getFavouriteStations() {
        RealmResults<CacheCity> cacheCities = realm.where(CacheCity.class).findAll();
        return realm.copyFromRealm(cacheCities);
    }

    public Observable<List<MainWeatherModel>> getWeather(int[] idStations) {

        final ArrayList<CacheCity> favouriteCitys = new ArrayList<>(getFavouriteStations());

        if (!MyApplication.isNetworkConnected()) {
            RealmResults<MainWeatherModel> mainWeatherModels = realm.where(MainWeatherModel.class).findAll();
            return Observable.just(realm.copyFromRealm(mainWeatherModels));
        }


        ArrayList<Observable<MainWeatherModel>> observables = new ArrayList<>();

        for (int i = 0; i < idStations.length; i++) {
            observables.add(apiWeather.getWeatherByIdCity(idStations[i], 7, ApiModule.units, BuildConfig.API_KEY));
        }

        Observable<MainWeatherModel> result = Observable.merge(observables)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return result.concatMap(new Func1<MainWeatherModel, Observable<MainWeatherModel>>() {
            @Override
            public Observable<MainWeatherModel> call(MainWeatherModel mainWeatherModel) {
                mainWeatherModel.setCityId(mainWeatherModel.getCity().getId());

                for (CacheCity cacheCity : favouriteCitys) {
                    if (cacheCity.getId() == mainWeatherModel.getCityId()) {
                        for (WeatherListElement item : mainWeatherModel.getList()) {
                            item.setLocalDt(item.getDt() + cacheCity.getUtcOffset() + cacheCity.getDstOffset());
                        }
                    }
                }


                realm.beginTransaction();
                realm.copyToRealmOrUpdate(mainWeatherModel);
                realm.commitTransaction();
                return Observable.just(mainWeatherModel);
            }
        }).map(new Func1<MainWeatherModel, List<MainWeatherModel>>() {
            @Override
            public List<MainWeatherModel> call(MainWeatherModel mainWeatherModel) {
                RealmResults<MainWeatherModel> mainWeatherModels = realm.where(MainWeatherModel.class).findAll();
                return realm.copyFromRealm(mainWeatherModels);
            }
        });
    }

    public Observable<TimeZone> getTimeZoneByCoordinate(double latitude, double longitude, long timestamp) {
        String coordinate = latitude + "," + longitude;

        if (MyApplication.isNetworkConnected()) {
            return apiTimeZone.getTimeZoneByCoordinate(coordinate, timestamp, BuildConfig.G_API_KEY);
        } else {
            return Observable.error(new CustomException(CustomException.NETWORK_EXCEPTION, "Нет подключения"));
        }
    }
}