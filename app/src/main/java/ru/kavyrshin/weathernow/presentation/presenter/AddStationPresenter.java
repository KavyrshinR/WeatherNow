package ru.kavyrshin.weathernow.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.entity.CacheCity;
import ru.kavyrshin.weathernow.entity.Coord;
import ru.kavyrshin.weathernow.entity.StationListElement;
import ru.kavyrshin.weathernow.entity.TimeZone;
import ru.kavyrshin.weathernow.model.DataManager;
import ru.kavyrshin.weathernow.model.exception.CustomException;
import ru.kavyrshin.weathernow.presentation.view.AddStationView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class AddStationPresenter extends BasePresenter<AddStationView> {

    public static final String TAG = "myLogs";

    private String addingCityName = "";
    private ArrayList<StationListElement> stationListElements = new ArrayList<>();

    private DataManager dataManager = DataManager.getInstance();

    @Inject
    public AddStationPresenter() {
    }

    public void getArroundStations(String name, double latitude, double longitude) {
        getViewState().showLoad();
        addingCityName = name;

        dataManager.getStationArround(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<StationListElement>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideLoad();
                        CustomException customException;
                        if (e instanceof CustomException) {
                            customException = (CustomException) e;
                        } else {
                            e.printStackTrace();
                            customException = new CustomException(CustomException.UNKNOWN_EXCEPTION, "Неизвестная ошибка");
                        }

                        switch (customException.getId()) {
                            case CustomException.SERVER_EXCEPTION:
                            case CustomException.UNKNOWN_EXCEPTION: {
                                getViewState().showError(customException.getMessage());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onNext(List<StationListElement> nextStationListElements) {
                        getViewState().hideLoad();
                        stationListElements.clear();
                        stationListElements.addAll(nextStationListElements);//TODO: БЛять. По хорошему это должно быть в интеракторе(?)
                        getViewState().showArroundStations(nextStationListElements);
                    }
                });
    }


    public void addStation(int apiCityId) {
        StationListElement element = null;

        final CacheCity city = new CacheCity();
        city.setName(addingCityName);
        city.setId(apiCityId);

        for (StationListElement item : stationListElements) {
            if (apiCityId == item.getId()) {
                element = item;
            }
        }

        if (element != null) {
            Coord coord = element.getCoord();
            unsubscribeOnDestroy(
                    dataManager.getTimeZoneByCoordinate(coord.getLat(), coord.getLon(), element.getDt())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TimeZone>() {
                        @Override
                        public void onCompleted() {
                            Log.d("myLogs", "А onCompleted() вызывается вообще?");
                        }

                        @Override
                        public void onError(Throwable e) {
                            getViewState().hideLoad();
                            CustomException customException;
                            if (e instanceof CustomException) {
                                customException = (CustomException) e;
                            } else {
                                e.printStackTrace();
                                customException = new CustomException(CustomException.UNKNOWN_EXCEPTION, "Неизвестная ошибка");
                            }

                            switch (customException.getId()) {
                                case CustomException.SERVER_EXCEPTION:
                                case CustomException.UNKNOWN_EXCEPTION: {
                                    getViewState().showError(customException.getMessage());
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onNext(TimeZone timeZone) {
                            city.setUtcOffset(timeZone.getRawOffset());
                            city.setDstOffset(timeZone.getDstOffset());
                            dataManager.saveStation(city);
                            getViewState().stationAdded();
                        }
                    })
            );
        }
    }
}