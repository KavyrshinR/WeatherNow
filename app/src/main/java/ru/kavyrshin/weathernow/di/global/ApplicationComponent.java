package ru.kavyrshin.weathernow.di.global;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ru.kavyrshin.weathernow.MyApplication;
import ru.kavyrshin.weathernow.di.AddStationComponent;
import ru.kavyrshin.weathernow.di.DetailedWeatherComponent;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);

        ApplicationComponent build();
    }

    DetailedWeatherComponent.Builder detailedWeatherComponent();
    AddStationComponent.Builder addStationComponent();
}
