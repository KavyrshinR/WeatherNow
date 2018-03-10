package ru.kavyrshin.weathernow.data.repositories;


import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.kavyrshin.weathernow.BuildConfig;
import ru.kavyrshin.weathernow.data.api.ApiWeather;
import ru.kavyrshin.weathernow.data.database.AppDatabase;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.DataSource;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.models.WeatherListElement;
import ru.kavyrshin.weathernow.domain.repositories.IWeatherRepository;

public class WeatherRepository implements IWeatherRepository {

    private ApiWeather apiWeather;
    private AppDatabase database;

    @Inject
    public WeatherRepository(@Named("apiWeather") ApiWeather apiWeather, AppDatabase database) {
        this.apiWeather = apiWeather;
        this.database = database;
    }


    public Observable<Pair<DataSource, List<MainWeatherModel>>> getWeather(final List<CacheCity> favouriteCities) {

        ArrayList<Observable<MainWeatherModel>> observables = new ArrayList<>();

        for (int i = 0; i < favouriteCities.size(); i++) {
            observables.add(apiWeather.getWeatherByIdCity(favouriteCities.get(i).getId(), 7, BuildConfig.API_KEY).toObservable());
        }


        return Observable.mergeDelayError(observables)
                .concatMap(new Function<MainWeatherModel, ObservableSource<MainWeatherModel>>() {
                    @Override
                    public ObservableSource<MainWeatherModel> apply(MainWeatherModel mainWeatherModel) throws Exception {
                        mainWeatherModel.setCityId(mainWeatherModel.getCity().getId());

                        for (CacheCity cacheCity : favouriteCities) {
                            if (cacheCity.getId() == mainWeatherModel.getCityId()) {
                                mainWeatherModel.getCity().setName(cacheCity.getName());
                                for (WeatherListElement item : mainWeatherModel.getList()) {
                                    item.setLocalDt(item.getDt() + cacheCity.getUtcOffset() + cacheCity.getDstOffset());

                                }
                            }
                        }

                        database.saveWeather(mainWeatherModel);

                        return Observable.just(mainWeatherModel);
                    }
                })
                .buffer(favouriteCities.size())
                .map(new Function<List<MainWeatherModel>, Pair<DataSource,List<MainWeatherModel>>>() {

                    @Override
                    public Pair<DataSource, List<MainWeatherModel>> apply(List<MainWeatherModel> mainWeatherModels) throws Exception {
                        List<MainWeatherModel> mainWeatherModelList = database.getAllWeather();

                        return new Pair<>(new DataSource(DataSource.INTERNET_DATA_SOURCE), mainWeatherModelList);
                    }
                })
                .startWith(getAllCachedWeather().toObservable());
    }

    @Override
    public Single<Pair<DataSource, List<MainWeatherModel>>> getAllCachedWeather() {
        List<MainWeatherModel> mainWeatherModelList = database.getAllWeather();
        Pair<DataSource, List<MainWeatherModel>> modelPair
                = new Pair<>(new DataSource(DataSource.DISK_DATA_SOURCE), mainWeatherModelList);
        return Single.just(modelPair);
    }

    @Override
    public Single<MainWeatherModel> getWeatherByCityId(int cityId) {
        MainWeatherModel weatherModel = database.getWeatherById(cityId);
        return Single.just(weatherModel);
    }

    @Override
    public Single<Boolean> deleteWeatherByCityId(int cityId) {
        return Single.just(database.deleteWeatherByStationId(cityId));
    }
}