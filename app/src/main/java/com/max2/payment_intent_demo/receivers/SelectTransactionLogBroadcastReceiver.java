package com.max2.payment_intent_demo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.max2.payment_intent_demo.App;
import com.max2.payment_intent_demo.events.SelectTransactionEvent;
import com.max2.veeaconnect.sdk.domain.Log;
import com.max2.veeaconnect.sdk.domain.entities.TransactionLog;
import com.max2.veeaconnect.sdk.ui.common.interfaces.sdk.SelectTransactionLogRequestSupport;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SelectTransactionLogBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SelectTransactionLogBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            Log.d(TAG, "Empty intent or bundle, skipping");
        } else {
            boolean isOperationSuccessful = intent.getExtras().getBoolean(SelectTransactionLogRequestSupport.KEY_ACTION_COMPLETED, false);
            String transactionLogJson = intent.getExtras().getString(SelectTransactionLogRequestSupport.KEY_TRANSACTION_LOG);
            String linkedTransactionLogsJson = intent.getExtras().getString(SelectTransactionLogRequestSupport.KEY_LINKED_TRANSACTION_LOGS);

            Type linkedTransactionLogsType = new TypeToken<HashMap<Long, TransactionLog>>() {
            }.getType();

            Log.d(TAG, "Received, success is " + isOperationSuccessful);
            Log.d(TAG, "Transaction log is: " + transactionLogJson);
            Log.d(TAG, "Linked transaction logs are: " + linkedTransactionLogsJson);

            App.getInstance().getEventBus().post(new SelectTransactionEvent(
                    isOperationSuccessful,
                    App.getInstance().getGson().fromJson(transactionLogJson, TransactionLog.class),
                    App.getInstance().getGson().fromJson(linkedTransactionLogsJson, linkedTransactionLogsType)
            ));
        }
    }
}
