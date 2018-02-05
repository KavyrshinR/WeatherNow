package ru.kavyrshin.weathernow.di;

import dagger.Subcomponent;
import ru.kavyrshin.weathernow.presentation.presenter.SettingsPresenter;

@Subcomponent
public interface SettingsScreenComponent {

    @Subcomponent.Builder
    interface Builder {
        SettingsScreenComponent build();
    }

    SettingsPresenter presenter();
}