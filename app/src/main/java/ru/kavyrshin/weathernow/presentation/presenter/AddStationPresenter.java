package ru.kavyrshin.weathernow.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import ru.kavyrshin.weathernow.data.exception.CustomException;
import ru.kavyrshin.weathernow.domain.interactors.AddStationInteractor;
import ru.kavyrshin.weathernow.domain.models.CacheCity;
import ru.kavyrshin.weathernow.domain.models.StationListElement;
import ru.kavyrshin.weathernow.presentation.view.AddStationView;

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
                    .subscribeWith(new DisposableSingleObserver<List<StationListElement>>() {

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
                        public void onSuccess(List<StationListElement> stationListElements) {
                            getViewState().hideLoad();
                            getViewState().showArroundStations(stationListElements);
                        }
                    })
        );
    }


    public void addStation(int apiCityId) {

        unsubscribeOnDestroy(
                addStationInteractor.saveStation(apiCityId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CacheCity>() {

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
                    public void onSuccess(CacheCity cacheCity) {
                        if (cacheCity != null) {
                            getViewState().stationAdded();
                        }
                    }
                })
        );
    }
}