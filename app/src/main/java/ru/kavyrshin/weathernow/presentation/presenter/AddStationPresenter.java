package ru.kavyrshin.weathernow.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.data.exception.CustomException;
import ru.kavyrshin.weathernow.domain.interactors.AddStationInteractor;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.presentation.view.AddStationView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

@InjectViewState
public class AddStationPresenter extends BasePresenter<AddStationView> {

    public static final String TAG = "myLogs";

    private AddStationInteractor addStationInteractor;

    @Inject
    public AddStationPresenter(AddStationInteractor addStationInteractor) {
        this.addStationInteractor = addStationInteractor;
    }

    public void getArroundStations(double latitude, double longitude) {
        getViewState().showLoad();

        unsubscribeOnDestroy(
            addStationInteractor.getStationsArround(latitude, longitude)
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
                            getViewState().showArroundStations(nextStationListElements);
                        }
                    })
        );
    }


    public void addStation(int apiCityId) {

        unsubscribeOnDestroy(
                addStationInteractor.saveStation(apiCityId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CacheCity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("myLogs", "onCompleted()");
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
                    public void onNext(CacheCity cacheCity) {
                        if (cacheCity != null) {
                            getViewState().stationAdded();
                        }
                    }
                })
        );
    }
}