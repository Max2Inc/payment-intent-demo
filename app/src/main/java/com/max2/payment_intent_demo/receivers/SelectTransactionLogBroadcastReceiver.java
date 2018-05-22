package com.max2.payment_intent_demo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.max2.veeaconnect.sdk.domain.Log;
import com.max2.veeaconnect.sdk.ui.common.interfaces.SelectTransactionLogRequestSupport;

public class SelectTransactionLogBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SelectTransactionLogBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            Log.d(TAG, "Empty intent or bundle, skipping");
        } else {
            Log.d(TAG, "Received, success is " +
                    intent.getExtras().getBoolean(SelectTransactionLogRequestSupport.KEY_ACTION_COMPLETED, false));
            Log.d(TAG, "Transaction log is: " +
                    intent.getExtras().getString(SelectTransactionLogRequestSupport.KEY_TRANSACTION_LOG));
        }
    }
}
