package ru.kavyrshin.weathernow.di;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;
import ru.kavyrshin.weathernow.presentation.presenter.DetailedWeatherPresenter;

@Subcomponent
public interface DetailedWeatherComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder cityId(@Named("cityId") int cityId);
        @BindsInstance
        Builder unixTime(@Named("unixTime") int unixTime);

        DetailedWeatherComponent build();
    }

    DetailedWeatherPresenter presenter();
}