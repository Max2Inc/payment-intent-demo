package com.max2.payment_intent_demo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.max2.payment_intent_demo.App;
import com.max2.payment_intent_demo.events.AuthenticationEvent;
import com.max2.veeaconnect.sdk.domain.Log;
import com.max2.veeaconnect.sdk.ui.common.interfaces.AuthenticationRequestSupport;

public class AuthenticationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AuthenticationBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getExtras() != null) {
                boolean authenticationResult;

                Log.d(TAG, "Received intent with status " +
                        (authenticationResult = intent.getBooleanExtra(AuthenticationRequestSupport.KEY_AUTHENTICATION_STATUS, false)));

                App.getInstance().getEventBus().post(new AuthenticationEvent(authenticationResult));
            } else {
                Log.d(TAG, "Received empty intent");
            }
        }
    }
}
