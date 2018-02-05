package ru.kavyrshin.weathernow.di;

import dagger.Subcomponent;
import ru.kavyrshin.weathernow.presentation.presenter.MyStationsPresenter;

@Subcomponent
public interface MyStationsComponent {

    @Subcomponent.Builder
    interface Builder {
        MyStationsComponent build();
    }

    MyStationsPresenter presenter();
}