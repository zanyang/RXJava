package com.sangebaba.rxjava.application;

import android.app.Application;


public class AppApplication extends Application {

    private static AppApplication sInstance;

    public static AppApplication getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;
    }

}
