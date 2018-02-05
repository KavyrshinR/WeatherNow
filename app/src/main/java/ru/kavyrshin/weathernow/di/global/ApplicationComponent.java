package ru.kavyrshin.weathernow.di.global;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ru.kavyrshin.weathernow.MyApplication;
import ru.kavyrshin.weathernow.di.AddStationComponent;
import ru.kavyrshin.weathernow.di.DetailedWeatherComponent;
import ru.kavyrshin.weathernow.di.MyStationsComponent;
import ru.kavyrshin.weathernow.di.SettingsScreenComponent;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);

        ApplicationComponent build();
    }

    MyStationsComponent.Builder myStationsComponent();
    DetailedWeatherComponent.Builder detailedWeatherComponent();
    AddStationComponent.Builder addStationComponent();
    SettingsScreenComponent.Builder settingsScreenComponent();
}
