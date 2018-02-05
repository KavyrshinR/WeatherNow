package ru.kavyrshin.weathernow.di;

import dagger.Subcomponent;
import ru.kavyrshin.weathernow.presentation.presenter.AddStationPresenter;


@Subcomponent
public interface AddStationComponent {

    @Subcomponent.Builder
    interface Builder {
        AddStationComponent build();
    }

    AddStationPresenter presenter();
}
