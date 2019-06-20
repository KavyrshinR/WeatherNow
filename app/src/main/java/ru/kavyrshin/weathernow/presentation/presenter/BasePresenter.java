package ru.kavyrshin.weathernow.presentation.presenter;


import androidx.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

//    private ArrayCompositeSubscription compositeSubscription = new ArrayCompositeSubscription(20);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

//    protected void unsubscribeOnDestroy(@NonNull Subscription subscription) {
//        compositeSubscription.set(compositeSubscription.length(), subscription);
//    }

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        compositeSubscription.dispose();
        compositeDisposable.dispose();
    }
}