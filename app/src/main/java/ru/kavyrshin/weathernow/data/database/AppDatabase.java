package ru.kavyrshin.weathernow.data.database;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;

public class AppDatabase {

    public void saveWeather(MainWeatherModel mainWeatherModel) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(mainWeatherModel);
        realm.commitTransaction();

        realm.close();
    }

    public List<MainWeatherModel> getAllWeather() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<MainWeatherModel> mainWeatherModels = realm.where(MainWeatherModel.class).findAll();
        List<MainWeatherModel> mainWeatherModelList = realm.copyFromRealm(mainWeatherModels);

        realm.close();

        return  mainWeatherModelList;
    }

    public MainWeatherModel getWeatherById(int cityId) {
        Realm realm = Realm.getDefaultInstance();

        MainWeatherModel mainWeatherModelRealm =
                realm.where(MainWeatherModel.class).equalTo("cityId", cityId).findFirst();
        MainWeatherModel mainWeatherModel = realm.copyFromRealm(mainWeatherModelRealm);
        realm.close();

        return mainWeatherModel;
    }

    public List<CacheCity> getAllCacheCity() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<CacheCity> cacheCities = realm.where(CacheCity.class).findAll();
        List<CacheCity> cacheCitiesList = realm.copyFromRealm(cacheCities);

        realm.close();
        return cacheCitiesList;
    }

    public Observable<CacheCity> saveCacheCity(CacheCity city) {
        CacheCity result = null;
        final Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        result = realm.copyToRealmOrUpdate(city);
        realm.commitTransaction();

        realm.close();
        return Observable.just(result);
    }

    public boolean deleteWeatherByStationId(int cityId) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        RealmResults<MainWeatherModel> results = realm.where(MainWeatherModel.class).equalTo("cityId", cityId).findAll();
        boolean result = results.deleteAllFromRealm();

        realm.commitTransaction();

        realm.close();

        return result;
    }

    public boolean deleteCacheCity(int cityId) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        final RealmResults<CacheCity> results = realm.where(CacheCity.class).equalTo("id", cityId).findAll();
        boolean result = results.deleteAllFromRealm();

        realm.commitTransaction();

        realm.close();

        return result;
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

        if (!realmResults.isEmpty()) {
            weatherSettings = realm.copyFromRealm(realmResults.first());
        } else {
            weatherSettings = new WeatherSettings();
        }
        realm.close();

        return weatherSettings;
    }
}