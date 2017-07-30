package ru.kavyrshin.weathernow.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.kavyrshin.weathernow.entity.CacheCity;
import ru.kavyrshin.weathernow.entity.StationListElement;
import ru.kavyrshin.weathernow.model.DataManager;
import ru.kavyrshin.weathernow.model.exception.CustomException;
import ru.kavyrshin.weathernow.view.AddStationView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class AddStationPresenter extends BasePresenter<AddStationView> {

    private String addingCityName = "";

    private DataManager dataManager = DataManager.getInstance();

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
                    public void onNext(List<StationListElement> stationListElements) {
                        getViewState().hideLoad();
                        getViewState().showArroundStations(stationListElements);
                    }
                });
    }


    public void addStation(int apiCityId) {
        CacheCity city = new CacheCity();
        city.setName(addingCityName);
        city.setId(apiCityId);
        dataManager.saveStation(city);
    }
}