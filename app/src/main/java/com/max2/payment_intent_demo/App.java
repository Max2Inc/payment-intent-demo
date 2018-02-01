package com.max2.payment_intent_demo;

import android.app.Application;

import com.google.gson.Gson;
import com.squareup.otto.Bus;

public class App extends Application {
    protected static App me;

    private final Bus eventBus = new Bus();
    private final Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();

        me = this;
    }

    public static App getInstance() {
        return me;
    }

    public Bus getEventBus() {
        return eventBus;
    }

    public Gson getGson() {
        return gson;
    }
}
