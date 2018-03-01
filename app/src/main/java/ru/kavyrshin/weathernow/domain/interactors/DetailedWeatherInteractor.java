package ru.kavyrshin.weathernow.domain.interactors;

import java.util.ArrayList;

import javax.inject.Inject;

import ru.kavyrshin.weathernow.domain.models.ConcreteWeather;
import ru.kavyrshin.weathernow.domain.models.MainWeatherModel;
import ru.kavyrshin.weathernow.domain.models.WeatherListElement;
import ru.kavyrshin.weathernow.domain.repositories.ISettingsRepository;
import ru.kavyrshin.weathernow.domain.repositories.IWeatherRepository;
import ru.kavyrshin.weathernow.util.Utils;
import ru.kavyrshin.weathernow.util.WeatherSettings;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DetailedWeatherInteractor {

    private IWeatherRepository weatherRepository;
    private ISettingsRepository settingsRepository;

    @Inject
    public DetailedWeatherInteractor(IWeatherRepository weatherRepository, ISettingsRepository settingsRepository) {
        this.weatherRepository = weatherRepository;
        this.settingsRepository = settingsRepository;
    }

    public Observable<ConcreteWeather> getWeatherByCityId(int cityId, final int time) {
        return weatherRepository.getWeatherByCityId(cityId)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<MainWeatherModel, Observable<ConcreteWeather>>() {
                    @Override
                    public Observable<ConcreteWeather> call(MainWeatherModel mainWeatherModel) {
                        ArrayList<WeatherListElement> weatherListElements = new ArrayList<>(mainWeatherModel.getList());
                        final ConcreteWeather result = new ConcreteWeather();
                        result.setCityName(mainWeatherModel.getCity().getName());

                        for (int i = 0; i < weatherListElements.size(); i++) {
                            if (weatherListElements.get(i).getDt() == time) {
                                result.setWeatherListElement(weatherListElements.get(i));
                            }
                        }

                        if (result.getWeatherListElement() != null) {

                            return settingsRepository.getWeatherSettings()
                                    .flatMap(new Func1<WeatherSettings, Observable<ConcreteWeather>>() {
                                        @Override
                                        public Observable<ConcreteWeather> call(WeatherSettings weatherSettings) {
                                            Utils.convertWeatherUnit(result.getWeatherListElement(), weatherSettings);
                                            return Observable.just(result);
                                        }
                                    });
                        } else {
                            return Observable.error(new RuntimeException("unexpected weather"));
                        }
                    }
                });
    }
}