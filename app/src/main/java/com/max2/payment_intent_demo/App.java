package com.max2.payment_intent_demo;

import android.app.Application;

import com.google.gson.Gson;
import com.max2.veeaconnect.sdk.Max2SdkInternal;
import com.squareup.otto.Bus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class App extends Application {
    protected static App me;

    private final Bus eventBus = new Bus();
    private final Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();

        me = this;

        initMax2Sdk();
    }

    protected void initMax2Sdk() {
        Max2SdkInternal.builder(this)
                .subscribeOnScheduler(Schedulers.io())
                .observeOnScheduler(AndroidSchedulers.mainThread())
                .baseUrl(BuildConfig.ENDPOINT_URL) //provided by Veea
                .debug(BuildConfig.DEBUG)
                .kiccIpAddress(BuildConfig.KICC_IP_ADDRESS) //provided by Veea
                .kiccPort(BuildConfig.KICC_TRANSACTION_PORT) //provided by Veea
                .dummyPayments(BuildConfig.DUMMY_PAYMENTS) //if successful test transactions are needed
                .init();
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
