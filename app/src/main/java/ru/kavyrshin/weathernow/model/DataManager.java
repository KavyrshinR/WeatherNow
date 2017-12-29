package ru.kavyrshin.weathernow.model;


import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.kavyrshin.weathernow.BuildConfig;
import ru.kavyrshin.weathernow.MyApplication;
import ru.kavyrshin.weathernow.entity.CacheCity;
import ru.kavyrshin.weathernow.entity.DataSource;
import ru.kavyrshin.weathernow.entity.MainStationModel;
import ru.kavyrshin.weathernow.entity.MainWeatherModel;
import ru.kavyrshin.weathernow.entity.StationListElement;
import ru.kavyrshin.weathernow.entity.TimeZone;
import ru.kavyrshin.weathernow.entity.WeatherListElement;
import ru.kavyrshin.weathernow.model.api.ApiModule;
import ru.kavyrshin.weathernow.model.api.ApiTimeZone;
import ru.kavyrshin.weathernow.model.api.ApiWeather;
import ru.kavyrshin.weathernow.model.exception.CustomException;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataManager {

    public static final String TAG = "myLogs";
    public static final double hPa_IN_mmHg = 0.75006375541921;

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
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(city);
        realm.commitTransaction();

        realm.close();
    }

    public List<CacheCity> getFavouriteStations() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<CacheCity> cacheCities = realm.where(CacheCity.class).findAll();
        List<CacheCity> cacheCitiesList = realm.copyFromRealm(cacheCities);

        realm.close();
        return cacheCitiesList;
    }

    public void deleteFavouriteStation(int cityId) {
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<CacheCity> results = realm.where(CacheCity.class).equalTo("id", cityId).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });

        realm.close();
    }

    public void deleteWeatherByStationId(int cityId) {
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<MainWeatherModel> results = realm.where(MainWeatherModel.class).equalTo("cityId", cityId).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });

        realm.close();
    }

    public Pair<DataSource, List<MainWeatherModel>> getCachedWeather() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<MainWeatherModel> mainWeatherModels = realm.where(MainWeatherModel.class).findAll();
        List<MainWeatherModel> mainWeatherModelList = realm.copyFromRealm(mainWeatherModels);

        realm.close();
        return new Pair<>(new DataSource(DataSource.DISK_DATA_SOURCE), mainWeatherModelList);
    }

    public MainWeatherModel getCachedWeatherById(int cityId) {
        Realm realm = Realm.getDefaultInstance();

        MainWeatherModel mainWeatherModelRealm =
                realm.where(MainWeatherModel.class).equalTo("cityId", cityId).findFirst();
        MainWeatherModel mainWeatherModel = realm.copyFromRealm(mainWeatherModelRealm);
        realm.close();

        return mainWeatherModel;
    }

    public Observable<Pair<DataSource, List<MainWeatherModel>>> getWeather(int[] idStations) {

        final ArrayList<CacheCity> favouriteCitys = new ArrayList<>(getFavouriteStations());

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
                Realm realm = Realm.getDefaultInstance();

                mainWeatherModel.setCityId(mainWeatherModel.getCity().getId());

                for (CacheCity cacheCity : favouriteCitys) {
                    if (cacheCity.getId() == mainWeatherModel.getCityId()) {
                        for (WeatherListElement item : mainWeatherModel.getList()) {
                            item.setLocalDt(item.getDt() + cacheCity.getUtcOffset() + cacheCity.getDstOffset());
                            item.setPressure(item.getPressure() * hPa_IN_mmHg);
                        }
                    }
                }


                realm.beginTransaction();
                realm.copyToRealmOrUpdate(mainWeatherModel);
                realm.commitTransaction();

                realm.close();
                return Observable.just(mainWeatherModel);
            }
        }).map(new Func1<MainWeatherModel, Pair<DataSource, List<MainWeatherModel>>>() {
            @Override
            public Pair<DataSource, List<MainWeatherModel>> call(MainWeatherModel mainWeatherModel) {
                Realm realm = Realm.getDefaultInstance();

                RealmResults<MainWeatherModel> mainWeatherModels = realm.where(MainWeatherModel.class).findAll();
                List<MainWeatherModel> mainWeatherModelList = realm.copyFromRealm(mainWeatherModels);

                realm.close();

                return new Pair<>(new DataSource(DataSource.INTERNET_DATA_SOURCE), mainWeatherModelList);
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

    public void saveWeatherSettings(WeatherSettings weatherSettings) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(weatherSettings);
        realm.commitTransaction();
        realm.close();
    }

    public WeatherSettings getWeatherSettings() {
        WeatherSettings weatherSettings;

        Realm realm = Realm.getDefaultInstance();
        RealmResults<WeatherSettings> realmResults = realm.where(WeatherSettings.class).findAll();
        Log.d(TAG, "getWeatherSettings: size " + realmResults.size());
        if (!realmResults.isEmpty()) {
            weatherSettings = realm.copyFromRealm(realmResults.first());
        } else {
            weatherSettings = new WeatherSettings();
        }
        realm.close();

        return weatherSettings;
    }
}