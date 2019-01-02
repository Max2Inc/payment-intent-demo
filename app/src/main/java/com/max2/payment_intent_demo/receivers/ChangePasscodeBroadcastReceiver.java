package com.max2.payment_intent_demo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.max2.payment_intent_demo.App;
import com.max2.payment_intent_demo.events.ChangePasscodeEvent;
import com.max2.veeaconnect.sdk.domain.Log;
import com.max2.veeaconnect.sdk.ui.common.interfaces.sdk.PasscodeManagementRequestSupport;

public class ChangePasscodeBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ChangePasscodeBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            Log.d(TAG, "Empty intent or bundle, skipping");
        } else {
            boolean operationSucceeded = intent.getExtras().getBoolean(PasscodeManagementRequestSupport.KEY_ACTION_COMPLETED);

            Log.d(TAG, "Received, success is " + operationSucceeded);

            App.getInstance().getEventBus().post(new ChangePasscodeEvent(operationSucceeded));
        }
    }
}
