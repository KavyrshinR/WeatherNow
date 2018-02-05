package ru.kavyrshin.weathernow;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.kavyrshin.weathernow.di.global.ApplicationComponent;
import ru.kavyrshin.weathernow.di.global.DaggerApplicationComponent;

public class MyApplication extends Application {

    public static Context context;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);

        context = getApplicationContext();

        applicationComponent = DaggerApplicationComponent.builder().application(this).build();
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            throw new NullPointerException("Application component null");
        }
        return applicationComponent;
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void savePreferences(Set<String> sources) {
        SharedPreferences prefs = context.getSharedPreferences("Sources", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("sourceIds", sources);
        editor.apply();
    }

    public static Set<String> getPreferences() {
        SharedPreferences prefs = context.getSharedPreferences("Sources", MODE_PRIVATE);
        return prefs.getStringSet("sourceIds", new HashSet<String>());
    }
}